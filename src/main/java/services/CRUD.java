package services;

import java.sql.SQLException;
import java.util.List;

public interface CRUD<T> {

    void insertOne_prod(T t) throws SQLException;

    void updateOne_prod(T t) throws SQLException;

    void deleteOne_prod(T t) throws SQLException;

    List<T> selectAll_prod() throws SQLException;

    void insertOne_img(T t) throws SQLException;

    void updateOne_img(T t) throws SQLException;

    void deleteOne_img(T t) throws SQLException;

    List<T> selectAll_img() throws SQLException;
}
