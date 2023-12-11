package edu.eci.arsw.magicBrushstrokes.model;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Entity;

import org.apache.commons.math3.analysis.function.Identity;
import org.apache.poi.ss.formula.functions.Columns;

import lombok.Data;

@Entity
@Table(name = "players")
@Data
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name", nullable = false)
    private String name;
    // @Column(name="nick_name", nullable = false)
    // private String nickName;
    @Column(name="age", nullable = false)
    private int age;
    @Column(name="password", nullable = false)
    private String password;
    // @Column(name="very_password", nullable = false)
    // private String veryPassword;

    public Player(){

    }

    public Player(String name, int age, String password){
        this.name = name;
        this.age = age;
        this.password = password;
        
    }
}
