package services;

import java.sql.SQLException;
import java.util.List;

public interface CRUD<T> {

    void insertOne1(T t) throws SQLException;
    public boolean modifier(T t) throws SQLException;
    public boolean supprimer(T t) throws SQLException;
    List<T> selectAll() throws SQLException;

}
