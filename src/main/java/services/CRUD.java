package services;

import java.sql.SQLException;
import java.util.List;

public interface CRUD<T> {

    void insertOne1(T t) throws SQLException;
    public T getById(int id);
    public boolean modifier(T t) throws SQLException;
    public boolean supprimer(T t) throws SQLException;
    List<T> selectAll() throws SQLException;
    List<T> getAllByUser(int t);
    //public T findById(int t);

}
