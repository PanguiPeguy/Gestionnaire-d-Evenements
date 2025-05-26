package model;

public interface EvenementObservable {
    void addObserver(ParticipantObserver observer);
    void removeObserver(ParticipantObserver observer);
    void notifyObservers(String message);
}