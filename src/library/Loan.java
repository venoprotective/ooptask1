package library;

import java.time.LocalDate;

public class Loan {
    private final PhysicalBook copy;
    private final String reader;
    private final LocalDate issueDate; // данные о выпуске
    private final LocalDate dueDate; // срок сдачи
    private LocalDate returnDate;

    public Loan(PhysicalBook copy, String reader, LocalDate issueData, LocalDate dueData) {
        this.copy = copy;
        this.reader = reader;
        this.issueDate = issueData;
        this.dueDate = dueData;
    }


    public PhysicalBook getCopy() {
        return copy;
    }

    public String getReader() {
        return reader;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public boolean isActive(){
        return returnDate == null;
    }

    public boolean isOverdue(LocalDate date){
        return isActive() && dueDate.isBefore(date);
    }

    public void close(LocalDate returnDate){
        this.returnDate = returnDate;
    }

    public String toString(){
        return copy.getInventoryNumber() + "\n"
                + " читал " + reader + "\n"
                + " выдана: " + issueDate + "\n"
                + " вернуть до: " + dueDate;
    }
}
