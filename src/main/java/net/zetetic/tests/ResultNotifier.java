package main.java.net.zetetic.tests;

public interface ResultNotifier {
    void send(TestResult result);
    void complete();
}
