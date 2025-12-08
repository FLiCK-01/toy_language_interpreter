package controller;

import exception.MyException;
import exception.RepoException;
import model.PrgState;
import model.values.IValue;
import model.values.RefValue;
import repository.IRepo;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Controller implements IController {
    private ExecutorService executor;
    private IRepo repo;
    private boolean displayFlag = false;
    public Controller(IRepo repo) {
        this.repo = repo;
    }

    Map<Integer, IValue> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer, IValue> heap) {
        return heap.entrySet().stream().filter(e -> symTableAddr.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<PrgState> removeCompletedPrg(List<PrgState> prgs) {
        return prgs.stream().filter(p -> p.isNotCompleted()).collect(Collectors.toList());
    }

    List<Integer> getAddrFromValues(Collection<IValue> values) {
        return values.stream().filter(v -> v instanceof RefValue).map(v -> {
            RefValue v1 = (RefValue) v;
            return v1.getAddr();
        }).collect(toList());
    }


    @Override
    public void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException {
        prgList.forEach(prg -> {
            try{
                repo.logPrgState(prg);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (RepoException e) {
                throw new RuntimeException(e);
            }
        });

        List<Callable<PrgState>> callList = prgList.stream().map((PrgState p) -> (Callable<PrgState>) (() -> {
            return p.oneStep();
        })).collect(Collectors.toList());

        List<PrgState> newList = executor.invokeAll(callList).stream().map(future -> {
            try{
                return future.get();
            } catch(Exception e){
                System.out.println(e.getMessage());
                return null;
            }
        }).filter(p -> p != null).collect(Collectors.toList());

        prgList.addAll(newList);

        prgList.forEach(prg -> {
            try{
                repo.logPrgState(prg);
            } catch (IOException | RepoException e) {
                System.out.println(e.getMessage());
            }
        });

        repo.setPrgList(prgList);
    }

    public void allStep() throws InterruptedException, MyException {
        executor = Executors.newFixedThreadPool(2);

        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());

        while(prgList.size() > 0) {}
        List<Integer> symTableAddresses = prgList.stream().flatMap(p ->getAddrFromValues(p.getSymTable().getContent().values()).stream()).collect(Collectors.toList());
        List<Integer> heapAddresses = getAddrFromValues(prgList.get(0).getHeap().getContent().values());
        List<Integer> allActiveAddresses = Stream.concat(symTableAddresses.stream(), heapAddresses.stream()).distinct().collect(Collectors.toList());

        prgList.get(0).getHeap().setContent(
                safeGarbageCollector(allActiveAddresses, prgList.get(0).getHeap().getContent())
        );

        oneStepForAllPrg(prgList);

        prgList = removeCompletedPrg(repo.getPrgList());
        executor.shutdownNow();
        repo.setPrgList(prgList);
    }

    private List<PrgState> removeCompletedPrograms(List<PrgState> programList) {
        return programList.stream().filter(PrgState::isNotCompleted).toList();
    }


    @Override
    public void setDisplayFlag(boolean displayFlag) {
        this.displayFlag = displayFlag;
    }

    @Override
    public boolean getDisplayFlag() {
        return this.displayFlag;
    }

    @Override
    public void addPrgState(PrgState prgState) {
        repo.addPrgState(prgState);
    }

    @Override
    public void clearPrgState() {
        repo.clearPrgState();
    }

}
