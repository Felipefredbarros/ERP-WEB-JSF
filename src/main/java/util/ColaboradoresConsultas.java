/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author felip
 */
public class ColaboradoresConsultas {

    public static final String COUNT_CPFCNPJ = "SELECT COUNT(*) FROM Pessoa WHERE pes_cpfcnpj = :cpfDigitado AND pes_id <> :idPessoa";
    public static final String COUNT_RG = "SELECT COUNT(*) FROM Pessoa WHERE pes_rg = :rgDigitado AND pes_id <>  :idPessoa";
}
