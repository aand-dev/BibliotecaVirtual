package com.bibliotecav.bibliotecav.models.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "libros")
public class Libro implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String titulo;
    private String autor;
    private String genero;
    private Double precio;
    private int stock;
    private String imagen;
    private String sinopsis;

	@OneToMany(mappedBy = "libro")
    private List<ShoppingCartItem> shoppingCartItems;

    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

    public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

    public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}

    public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}

    public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}

    public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}

    public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getSinopsis() {
        return sinopsis;
    }
    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    private static final long serialVersionUID = 1L;

    public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
