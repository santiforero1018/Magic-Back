// package edu.eci.arsw.magicBrushstrokes.model;

// import javax.persistence.Id;
// import javax.persistence.Table;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Column;
// import javax.persistence.Entity;

// import org.apache.commons.math3.analysis.function.Identity;
// import org.apache.poi.ss.formula.functions.Columns;

// import lombok.Data;

// @Entity
// @Table(name = "players")
// @Data
// public class Player {
//     @Id
//     @GeneratedValue(strategy = GenerationType.AUTO)
//     private Long id;
//     private String name;
//     @Column(name="name", nullable = false)
//     private String nickName;
//     @Column(name="age", nullable = false)
//     private int age;
//     @Column(name="password", nullable = false)
//     private String password;
//     @Column(name="veryPassword", nullable = false)
//     private String veryPassword;

//     public Player(){

//     }

//     public Player(String name, String nickName, int age, String password, String veryPassword){
//         this.name = name;
//         this.nickName = nickName;
//         this.age = age;
//         this.password = password;
//         this.veryPassword = veryPassword;
//     }
// }
