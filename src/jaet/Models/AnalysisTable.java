package jaet.Models;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalysisTable {
    private ArrayList<String> production = new ArrayList<>();

    private String table [][] = {
            {" ","archivio","funzione","init","#","ALPHANUMERICS","^[a-zA-Z]$","_","^(=)$","^(\\+|\\-|\\*|/|\\^)$","while","for","enterokay","stamp","\"","true|false","^[0-9]$",",","if","else","scegliere","\'","default","break","return","^(<|>|<=|>=|==|!=)$","^(\\+\\+|\\-\\-)$","\\}",";","\\{","\\)","\\(","\n"},
            {"<DA>","archivio <V> { \n <DM> <CA> }"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "},
            {"<CA>"," ","<DFUN> <CA>"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," ","ε"," "," "," "," "," "},
            {"<DM>"," "," ","init main ( current_ ) { \n <CUR> } \n"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "},
            {"<DCM>"," "," "," ","# <RCM> \n"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "},
            {"<RCM>"," "," "," "," ","ALPHANUMERICS"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," ","{ <C> }"," "," ","ε"},
            {"<DV>"," "," "," "," "," ","<V> <RDV> \n","<V> <RDV> \n"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "},
            {"<RDV>"," "," "," "," "," "," "," ","= <S>"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," ","<CONT>"," "," "," "," ","( <P> )"," "},
            {"<DW>"," "," "," "," "," "," "," "," "," ","while := <EX> { \n <CUR> <RRET> } \n"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "},
            {"<DF>"," "," "," "," "," "," "," "," "," "," ","for := <EF> { \n <CUR> <RRET> } \n"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "},
            {"<EF>"," "," "," "," "," ","<V> = <S> ; <EX> ; <V> <CONT>","<V> = <S> ; <EX> ; <V> <CONT>"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "},
            {"<DE>"," "," "," "," "," "," "," "," "," "," "," ","enterokay ( <V> ) \n"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "},
            {"<DS>"," "," "," "," "," "," "," "," "," "," "," "," ","stamp ( <CONC> ) \n"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "},
            {"<CONC>"," "," "," "," "," ","<S> <RC>","<S> <RC>"," "," "," "," "," "," ","<S> <RC>","<S> <RC>","<S> <RC>"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "},
            {"<RC>"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," ",", <S> <RC>"," "," "," "," "," "," "," "," "," "," "," "," ","ε"," "," "},
            {"<DIF>"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," ","if := <EX> { \n <CUR> <RRET> } \n <RIF>"," "," "," "," "," "," "," "," "," "," "," "," "," "," "},
            {"<RIF>"," "," "," ","ε"," ","ε","ε"," "," ","ε","ε","ε","ε"," "," "," "," ","ε","else <RE>","ε"," "," "," ","ε"," "," ","ε"," "," "," "," ","ε"},
            {"<RE>"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," ","<DIF>"," "," "," "," "," "," "," "," "," "," ","{ \n <CUR> } \n"," "," "," "},
            {"<DFUN>"," ","funzione <V> ( <P> ) { \n <CUR> <RRET> } \n"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "},
            {"<P>"," "," "," "," "," ","<V> <RP>","<V> <RP> "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," ","ε"," "," "},
            {"<RP>"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," ",", <V> <RP>"," "," "," "," "," "," "," "," "," "," "," "," ","ε"," "," "},
            {"<DSC>"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," ","scegliere := <V> { \n <OP> } \n"," "," "," "," "," "," "," "," "," "," "," "," "},
            {"<OP>"," "," "," "," "," "," "," "," "," "," "," "," "," ","<TC> <ROPC>"," ","<TC> <ROPC>"," "," "," "," ","<TC> <ROPC>"," "," "," "," "," ","ε"," "," "," "," "," "},
            {"<ROPC>"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," ","default { \n <CUR> <RRET> } \n"," "," "," "," ","ε"," "," "," "," "," "},
            {"<TD>"," "," "," "," "," "," "," "," "," "," "," "," "," ","\" <C> \""," ","<N>"," "," "," "," ","\' <L> \'"," "," "," "," "," "," "," "," "," "," "," "},
            {"<TC>"," "," "," "," "," "," "," "," "," "," "," "," "," ","<TD> { \n <CUR> <RRET> } \n <RTC> <CT>"," ","<TD> { \n <CUR> <RRET> } \n <RTC> <CT>"," "," "," "," ","<TD> { \n <CUR> <RRET> } \n <RTC> <CT>"," "," "," "," "," "," "," "," "," "," "," "},
            {"<RTC>"," "," "," "," "," "," "," "," "," "," "," "," "," ","ε"," ","ε"," "," "," "," ","ε","ε","break \n"," "," "," ","ε"," "," "," "," "," "},
            {"<CT>"," "," "," "," "," "," "," "," "," "," "," "," "," ","<TC>"," ","<TC>"," "," "," "," ","<TC>","ε"," "," "," "," ","ε"," "," "," "," "," "},
            {"<DR>"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," ","return <S> \n"," "," "," "," "," "," "," "," "},
            {"<V>"," "," "," "," "," ","<L> <RV>","_ <RV>"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "},
            {"<LLF>"," "," "," "," "," "," "," ","ε"," "," "," "," "," "," "," "," ","ε"," "," "," "," "," "," ","ε","ε"," ","ε","ε","ε","ε","( <P> )","ε"},
            {"<C>"," "," "," "," ","ALPHANUMERICS"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "},
            {"<CUR>"," "," "," ","<DCM> <CUR>"," ","<DV> <CUR>","<DV> <CUR>"," "," ","<DW> <CUR>","<DF> <CUR>","<DE> <CUR>","<DS> <CUR>"," "," "," "," ","<DIF> <CUR>"," ","<DSC> <CUR>"," "," "," ","ε"," "," ","ε"," "," "," "," "," "},
            {"<RV>"," "," "," "," "," ","<L> <RV>","_ <RV>","ε"," "," "," "," "," "," "," ","<D> <RV>","ε"," "," "," "," "," "," ","ε","ε","ε","ε","ε","ε","ε","ε","ε"},
            {"<EX>"," "," "," "," "," ","<S> <OR> <S>","<S> <OR> <S>"," "," "," "," "," "," ","<S> <OR> <S>","<S> <OR> <S>","<S> <OR> <S>"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "},
            {"<S>"," "," "," "," "," ","<V> <LLF>","<V> <LLF>"," "," "," "," "," "," ","\" <C> \"","<B>","<OPR>"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "},
            {"<OPR>"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," ","<N> <RO>"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "},
            {"<RO>"," "," "," "," "," "," "," ","ε","<O> <N> <RO>"," "," "," "," "," "," "," ","ε"," "," "," "," "," "," ","ε","ε"," ","ε","ε","ε","ε"," ","ε"},
            {"<N>"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," ","<D> <CF>"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "},
            {"<CF>"," "," "," "," "," "," "," "," ","ε"," "," "," "," "," "," ","<D> <CF>","ε"," "," "," "," "," "," ","ε","ε"," ","ε","ε","ε","ε"," ","ε"},
            {"<RRET>"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," ","<DR>"," "," ","ε"," "," "," "," "," "},
            {"<OR>"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," ","^(<|>|<=|>=|==|!=)$"," "," "," "," "," "," "," "},
            {"<B>"," "," "," "," "," "," "," "," "," "," "," "," "," "," ","true|false"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "},
            {"<O>"," "," "," "," "," "," "," "," ","^(\\+|\\-|\\*|/|\\^)$"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "},
            {"<D>"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," ","^[0-9]$"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "},
            {"<L>"," "," "," "," "," ","^[a-zA-Z]$"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "},
            {"<CONT>"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," ","^(\\+\\+|\\-\\-)$"," "," "," "," "," "," "},



    };

    public void contarTabla(){
        for(int i = 0;i<table.length;i++){
            System.out.println(i+" - "+table[i].length);
        }
    }

    public boolean existDerivation(String notTerminal, String terminal){
        production.clear();
        String[] words;
        int row=0, column=0;
        String[] columns;
        String derivation;
        for (int i = 0; i < table.length; i++) {
            columns = table[i];
            for(int j=0; j<columns.length;j++){
                if(table[i][0].equals(notTerminal) && isValid(terminal, table[0][j])){
                    row = i;
                    column = j;
                    if(!table[row][column].equals(" ")){
                        if(table[i][0].equals("<L>") || table[i][0].equals("<O>") || table[i][0].equals("<B>") || table[i][0].equals("<D>") || table[i][0].equals("<OR>") || table[i][0].equals("<CONT>")){
                            table[i][j] = terminal;
                        }
                    }
                    break;
                }
            }
        }
        if(row == 0 || column==0){
            row=0;
            column=0;
        }
        derivation = table[row][column];
        if(!derivation.equals(" ")) {
            if(!derivation.equals("ε")){
                words = derivation.split(" ");
                for (int i = 0; i < words.length; i++) {
                    String word = words[i];
                    production.add(word);
                }
            }
            return true;
        }
        return false;

    }

    public ArrayList<String> getProduction() {
        return production;
    }

    public boolean isValid(String input, String regex){
        Pattern pattern;
        Matcher match;
        pattern = Pattern.compile(regex);
        match = pattern.matcher(input);
        return match.find();
    }

    public boolean isNotTerminal(String notTerminal){

        for (int i = 0; i < table.length; i++) {
            if(table[i][0].equals(notTerminal))
                return true;
        }
        return false;
    }
}
