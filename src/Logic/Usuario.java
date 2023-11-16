/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Stirven, Danna
 */
public class Usuario {

    
    // hubicacion de los archivos de los usuarios
    final String usuarios = "src\\Users\\";
    final static String usuariosList = "src\\Users\\";

    private String nombre_completo;
    private int edad;
    private long cC;
    private long saldo;
    private long telefono;
    private String direccion;
    private String email;
    private String usuario;
    private char[] contraseña;
    private String ultimo_login;
    private String usuario_temp;

    // costructores
    public Usuario() {
        this.nombre_completo = "SIN NOMBRE";
        this.edad = 0;
        this.cC = 0;
        this.saldo = 0;
        this.telefono = 0;
        this.direccion = "SIN DIRRECION";
        this.email = "SIN EMAIL";
        this.usuario = "SIN USUARIO";
        this.contraseña = null;
        this.ultimo_login = dataTime();
    }

    public Usuario(String usuario) {
        ArrayList<String> atributos = dataLastLine(usuario);

        this.usuario = atributos.get(0);
        this.contraseña = atributos.get(1).toCharArray();
        this.nombre_completo = atributos.get(2);
        this.cC = Long.parseLong(atributos.get(3));
        this.telefono = Long.parseLong(atributos.get(4));
        this.direccion = atributos.get(5);
        this.email = atributos.get(6);
        this.saldo = Integer.parseInt(atributos.get(7));
        this.edad = Integer.parseInt(atributos.get(8));
        this.ultimo_login = dataTime();
    }

    public ArrayList<String> dataLastLine(String usuario) {
        try {
            BufferedReader buferReader = new BufferedReader(new FileReader(usuarios + usuario + ".txt"));
            String str;
            String ultima_linea = "";
            while ((str = buferReader.readLine()) != null) {
                ultima_linea = str;
            }
            char[] linea = ultima_linea.toCharArray();
            ArrayList<String> atributos = new ArrayList<>();
            int j = 0;
            String txt;
            while (linea.length > j) {
                txt = "";
                while (linea[j] != ';') {
                    txt += linea[j];
                    j += 1;
                }
                j += 1;
                atributos.add(txt);
            }
            buferReader.close();
            return atributos;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // debuelve la lista de usuarios
    public static ArrayList<String> usuarios() {
        File file = new File(usuariosList);
        ArrayList<String> rList = new ArrayList<String>();
        String[] lista = file.list();
        for (int i = 0; i < lista.length; i++) {
            char[] contenido = lista[i].toCharArray();
            String str = "";
            for (int j = 0; contenido[j] != '.'; j++) {
                str += contenido[j];
            }
            rList.add(str);
        }
        return rList;

    }

    // debuelve la lista de usuarios
    public static ArrayList<Long> telefonos() {

        ArrayList<String> uList = new ArrayList<String>(usuarios());
        ArrayList<Long> telf = new ArrayList<>();
        Usuario user;
        for (int i = 0; i < uList.size(); i++) {
            user = new Usuario(uList.get(i));
            telf.add(user.getTelefono());
        }
        return telf;

    }

    public static boolean eliminar(String usuario) {
        if (usuarios().contains(usuario)) {
            File file = new File(usuariosList + usuario + ".txt");
            file.delete();
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<ArrayList<String>> historialCambios() {
        try {
            BufferedReader buferReader = new BufferedReader(new FileReader(usuarios + usuario + ".txt"));

            char[] linea;
            ArrayList<ArrayList<String>> historialArrayList = new ArrayList<>();

            int j = 0;
            String txt, txt2;

            // linea = buferReader.readLine().toCharArray();
            do {
                ArrayList<String> atributos = new ArrayList<>();
                if (( txt2=buferReader.readLine()) == null) {
                    break;
                }else{
                    linea = txt2.toCharArray();

                }
                while (linea.length > j) {
                    txt = "";
                    while (linea[j] != ';') {
                        txt += linea[j];
                        j += 1;
                    }
                    j += 1;
                    atributos.add(txt);
                }
                j = 0;
                historialArrayList.add(atributos);

            } while (true);

            ArrayList<ArrayList<String>> cambios = new ArrayList<>();
            for (int i = 0; i < historialArrayList.size() - 1; i++) {
                for (int k = 0; k < historialArrayList.get(i).size() - 1; k++) {
                   
                    if (!(historialArrayList.get(i).get(k).equals(historialArrayList.get(i + 1).get(k)))) {
                        ArrayList<String> t = new ArrayList<>();
                        t.add(historialArrayList.get(i + 1).get(k));
                        t.add(historialArrayList.get(i).get(historialArrayList.get(i).size()-1));
                        cambios.add(t);
                    }
                }
            }

            buferReader.close();
            return cambios;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // solo se usa dentro de esta clase para tomar el tiempo
    private String dataTime() {
        LocalDateTime time = LocalDateTime.now();
        int año = time.getYear();
        String hora;
        String minuto;
        String dia;
        String mes;
        if (time.getHour() < 10) {
            hora = "0" + time.getHour();
        } else {
            hora = String.valueOf(time.getHour());
        }
        if (time.getMinute() < 10) {
            minuto = "0" + time.getMinute();
        } else {
            minuto = String.valueOf(time.getMinute());
        }
        if (time.getDayOfMonth() < 10) {
            dia = "0" + time.getDayOfMonth();
        } else {
            dia = String.valueOf(time.getDayOfMonth());
        }
        if (time.getMonthValue() < 10) {
            mes = "0" + time.getMonthValue();
        } else {
            mes = String.valueOf(time.getMonthValue());
        }

        return (año + ":" + mes + ":" + dia + ":" + hora + ":" + minuto);
    }

    public boolean guardar() {
        try {
            if (this.usuario_temp != null) {
                File file = new File(usuarios + this.usuario + ".txt");
                // boolean bool = file.createNewFile();
                file.renameTo(new File(usuarios + this.usuario_temp + ".txt"));
                this.usuario = this.usuario_temp;
            }
            if (contraseña == null || contraseña == "".toCharArray()) {
                contraseña = "null".toCharArray();
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(usuarios + this.usuario + ".txt", true))) {
                bw.write(
                        usuario + ";"
                                + new String(contraseña) + ";"
                                + nombre_completo + ";"
                                + cC + ";"
                                + telefono + ";"
                                + direccion + ";"
                                + email + ";"
                                + saldo + ";"
                                + edad + ";"
                                + ultimo_login + ";");
                bw.newLine();
                bw.close();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // getters and zeters
    public String getNombre_completo() {
        return nombre_completo;
    }

    public int getEdad() {
        return edad;
    }

    public long getcC() {
        return cC;
    }

    public long getSaldo() {
        return saldo;
    }

    public String getSaldoFormat() {
        DecimalFormat decimalFormat = new DecimalFormat(",###");

        return decimalFormat.format(getSaldo());
    }

    public long getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getEmail() {
        return email;
    }

    public String getUsuario() {
        return usuario;
    }

    public char[] getContraseña() {
        return contraseña;
    }

    public String getUltimo_login() {
        return ultimo_login;
    }

    public void setNombre_completo(String nombre_completo) {

        String[] palabras = nombre_completo.split(" ");
        for (int i = 0; i < palabras.length; i++) {
            String primeraLetra = palabras[i].substring(0, 1).toUpperCase();
            String restoDeLaPalabra = palabras[i].substring(1);
            palabras[i] = primeraLetra + restoDeLaPalabra;
        }
        String resultado = String.join(" ", palabras);

        this.nombre_completo = resultado;

    }

    public void setEdad(int edad) {
        this.edad = edad;
        // guardar();
    }

    public void setcC(long cC) {
        this.cC = cC;
        // guardar();
    }

    public boolean setSaldo(long saldo) {
        long temp = this.saldo;
        temp += saldo;
        if (temp < 0) {
            return false;
        } else {
            this.saldo = temp;
            // guardar();
            return true;
        }

    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
        // guardar();
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
        // guardar();
    }

    public void setEmail(String email) {
        this.email = email;
        // guardar();
    }

    public void setUsuario(String usuario) {
        // File file = new File( usuarios+this.usuario + ".txt");
        // file.renameTo(new File( usuarios+usuario + ".txt"));
        this.usuario_temp = usuario;
    }

    public void setContraseña(char[] contraseña) {
        this.contraseña = contraseña;
        // guardar();
    }
    @Override
    public String toString() {
        return "Usuario [nombre_completo=" + nombre_completo + ", edad=" + edad + ", cC=" + cC + ", saldo=" + saldo
                + ", telefono=" + telefono + ", direccion=" + direccion + ", email=" + email + ", usuario=" + usuario
                + ", contraseña=" + Arrays.toString(contraseña) + ", ultimo_login=" + ultimo_login + "]";
    }

}
