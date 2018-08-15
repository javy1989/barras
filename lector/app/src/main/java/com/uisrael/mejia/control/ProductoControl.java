package com.uisrael.mejia.control;

import com.uisrael.mejia.model.Producto;

import java.util.List;

public interface ProductoControl {

    public List<Producto> listAll() throws Exception;
    public boolean addProducto(Producto producto);
}
