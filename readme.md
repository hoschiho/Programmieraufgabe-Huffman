# Programmieraufgabe Huffman

This is an implementation of the Huffman-Coding for Mada assignment by Pascal Hostettler.


1. Eine Textdatei (ASCII kodiert) soll eingelesen werden.
2. Es soll eine Tabelle angelegt werden, in der für jedes der 128 mö̈glichen ASCII-Zeichen drinsteht, wie oft das entsprechende Zeichen in der Textdatei vorkommt.
3. Aus dieser Häufigkeitstabelle soll eine Huffman-Kodierung fü̈r die Zeichen konstruiert werden, die in der Datei vorkommen.
4. Die Huffman-Kodierungstabelle soll in einer externen Datei dec tab.txt in der Form: ASCII-Code von Zeichen1:Code von Zeichen1-ASCII-Code von Zeichen2:Code von Zeichen2- . . . gespeichert werden.
5. Die eingelesene Textdatei soll entsprechend der Huffman-Kodierung in einen Bitstring kodiert werden.
6. An diesen Bitstring soll eine 1 und anschliessend so viele Nullen dran gehä̈ngt werden, bis der Bitstring eine Lä̈nge hat, die ein Vielfaches von 8 ist.
7. Aus diesem erweiterten Bitstring soll ein byte-Array erstellt werden, in dem je 8 aufein- anderfolgende Zeichen zu je einem byte zusammengefasst werden.
8. Dieses byte-Array soll in einer externen Datei output.dat gespeichert werden.
9. Das Programm soll zudem dekodieren kö̈nnen. Es soll also aus externen Dateien die Kodierungstabelle und das byte-Array eingelesen werden. Anschliessend soll das byte-Array in einen Bitstring umgewandelt werden, von dem dann die letzte 1 und alle folgenden Nullen abgeschnitten werden, um dann den resultierenden Bitstring zu dekodieren und in einer externen Datei decompress.txt zu speichern.
10. Testen Sie Ihr Programm an ein paar Textdateien und geben Sie an, wie viel Platz gespart wird.
11. Dekodieren Sie output-mada.dat mit dec tab-mada.txt.