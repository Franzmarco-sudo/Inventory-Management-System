package com.example.inventorymanagementsystem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO{

    public void addProduct(Product product) {

        String sql =
                "INSERT INTO products(product_name, quantity, price) VALUES(?,?,?)";

        try (
                Connection conn = DatabaseConnection.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, product.getProductName());
            stmt.setInt(2, product.getQuantity());
            stmt.setDouble(3, product.getPrice());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Product> getProducts() {

        List<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM products ORDER BY id";

        try (
                Connection conn = DatabaseConnection.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {

            while (rs.next()) {

                products.add(
                        new Product(
                                rs.getLong("id"),
                                rs.getString("product_name"),
                                rs.getInt("quantity"),
                                rs.getDouble("price")
                        )
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    public void updateProduct(Product product) {

        String sql =
                "UPDATE products SET product_name=?, quantity=?, price=? WHERE id=?";

        try (
                Connection conn = DatabaseConnection.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, product.getProductName());
            stmt.setInt(2, product.getQuantity());
            stmt.setDouble(3, product.getPrice());
            stmt.setLong(4, product.getId());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(long id) {

        String sql = "DELETE FROM products WHERE id=?";

        try (
                Connection conn = DatabaseConnection.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setLong(1, id);

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}