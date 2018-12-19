package day19;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import day16.Function;
import day4.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Integer.valueOf;


public class Main {

    private static final Map<String, Function<Integer>> OPERATIONS = collectOperations();

    public static void main(String[] args) throws IOException {
        String fileNameTest = "src/main/resources/day19/input_test.txt";
        String fileName = "src/main/resources/day19/input.txt";

//        System.out.println(part1(fileNameTest));
        System.out.println(part1(fileName));
//
//        System.out.println(part2(fileName));

    }

    private static int part1(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        List<String> instructions = lines.subList(1, lines.size());

        List<Integer> registers = Arrays.asList(0, 0, 0, 0, 0, 0);

        int instructionPointer = valueOf(lines.get(0).substring(lines.get(0).length() - 1));
        int instructionPointerValue = 0;

//        When the instruction pointer is bound to a register, its value is written to that register just before each
//        instruction is executed, and the value of that register is written back to the instruction pointer immediately
//        after each instruction finishes execution.
//        Afterward, move to the next instruction by adding one to the instruction pointer, even if the value in the
//        instruction pointer was just updated by an instruction. (Because of this, instructions must effectively set the
//        instruction pointer to the instruction before the one they want executed next.)
        while(instructionPointerValue < instructions.size()) {
            registers.set(instructionPointer, instructionPointerValue);

            String instructionString = instructions.get(instructionPointerValue);
            String opCode = instructionString.substring(0, instructionString.indexOf(" "));
            List<Integer> instruction = getInstruction(instructionString.substring(instructionString.indexOf(" ") + 1));

            registers = OPERATIONS.get(opCode).apply(instruction.get(0), instruction.get(1), instruction.get(2), registers);

            instructionPointerValue = registers.get(instructionPointer);
            instructionPointerValue++;
        }

        return registers.get(0);
    }

    private static Map<String, Function<Integer>>collectOperations() {
        Map<String, Function<Integer>> ops = new HashMap<>();

        ops.put("addi", Main::addi);
        ops.put("addr", Main::addr);
        ops.put("muli", Main::muli);
        ops.put("mulr", Main::mulr);
        ops.put("bani", Main::bani);
        ops.put("banr", Main::banr);
        ops.put("bori", Main::bori);
        ops.put("borr", Main::borr);
        ops.put("gtir", Main::gtir);
        ops.put("gtri", Main::gtri);
        ops.put("gtrr", Main::gtrr);
        ops.put("eqir", Main::eqir);
        ops.put("eqri", Main::eqri);
        ops.put("eqrr", Main::eqrr);
        ops.put("seti", Main::seti);
        ops.put("setr", Main::setr);

        return ops;
    }

    private static int part2(String fileName) throws IOException {

        return -1;
    }

    private static List<Integer> getInstruction(String instructionLine) {
        return Arrays.stream(instructionLine.split(" "))
                .map(String::trim)
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }

    private static List<Integer> getRegisters(List<String> lines, int i, int offset) {
        String[] registers = lines.get(i).substring(offset, lines.get(i).length() - 1).split(",");
        return Arrays.stream(registers)
                .map(String::trim)
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }

    //    addr (add register) stores into register C the result of adding register A and register B.
    private static List<Integer> addr(int A, int B, int C, List<Integer> registersBefore) {
        List<Integer> registersAfter = new ArrayList<>(registersBefore);
        registersAfter.set(C, registersBefore.get(A) + registersBefore.get(B));
        return registersAfter;
    }

    //    addi (add immediate) stores into register C the result of adding register A and value B
    private static List<Integer> addi(int A, int B, int C, List<Integer> registersBefore) {
        List<Integer> registersAfter = new ArrayList<>(registersBefore);
        registersAfter.set(C, registersBefore.get(A) + B);
        return registersAfter;
    }

    //    mulr (multiply register) stores into register C the result of multiplying register A and register B.
    private static List<Integer> mulr(int A, int B, int C, List<Integer> registersBefore) {
        List<Integer> registersAfter = new ArrayList<>(registersBefore);
        registersAfter.set(C, registersBefore.get(A) * registersBefore.get(B));
        return registersAfter;
    }

    //    muli (multiply immediate) stores into register C the result of multiplying register A and value B.
    private static List<Integer> muli(int A, int B, int C, List<Integer> registersBefore) {
        List<Integer> registersAfter = new ArrayList<>(registersBefore);
        registersAfter.set(C, registersBefore.get(A) * B);
        return registersAfter;
    }

    //    banr (bitwise AND register) stores into register C the result of the bitwise AND of register A and register B.
    private static List<Integer> banr(int A, int B, int C, List<Integer> registersBefore) {
        List<Integer> registersAfter = new ArrayList<>(registersBefore);
        registersAfter.set(C, registersBefore.get(A) & registersBefore.get(B));
        return registersAfter;
    }

    //    bani (bitwise AND immediate) stores into register C the result of the bitwise AND of register A and value B
    private static List<Integer> bani(int A, int B, int C, List<Integer> registersBefore) {
        List<Integer> registersAfter = new ArrayList<>(registersBefore);
        registersAfter.set(C, registersBefore.get(A) & B);
        return registersAfter;
    }

    //    borr (bitwise OR register) stores into register C the result of the bitwise OR of register A and register B.
    private static List<Integer> borr(int A, int B, int C, List<Integer> registersBefore) {
        List<Integer> registersAfter = new ArrayList<>(registersBefore);
        registersAfter.set(C, registersBefore.get(A) | registersBefore.get(B));
        return registersAfter;
    }

    //    bori (bitwise OR immediate) stores into register C the result of the bitwise OR of register A and value B.
    private static List<Integer> bori(int A, int B, int C, List<Integer> registersBefore) {
        List<Integer> registersAfter = new ArrayList<>(registersBefore);
        registersAfter.set(C, registersBefore.get(A) | B);
        return registersAfter;
    }

    //    setr (set register) copies the contents of register A into register C. (Input B is ignored.)
    private static List<Integer> setr(int A, int B, int C, List<Integer> registersBefore) {
        List<Integer> registersAfter = new ArrayList<>(registersBefore);
        registersAfter.set(C, registersBefore.get(A));
        return registersAfter;
    }

    //    seti (set immediate) stores value A into register C. (Input B is ignored.)
    private static List<Integer> seti(int A, int B, int C, List<Integer> registersBefore) {
        List<Integer> registersAfter = new ArrayList<>(registersBefore);
        registersAfter.set(C, A);
        return registersAfter;
    }


    //    gtir (greater-than immediate/register) sets register C to 1 if value A is greater than register B. Otherwise, register C is set to 0.
    private static List<Integer> gtir(int A, int B, int C, List<Integer> registersBefore) {
        List<Integer> registersAfter = new ArrayList<>(registersBefore);
        registersAfter.set(C, A > registersBefore.get(B) ? 1 : 0);
        return registersAfter;
    }

    //    gtri (greater-than register/immediate) sets register C to 1 if register A is greater than value B. Otherwise, register C is set to 0.
    private static List<Integer> gtri(int A, int B, int C, List<Integer> registersBefore) {
        List<Integer> registersAfter = new ArrayList<>(registersBefore);
        registersAfter.set(C, registersBefore.get(A) > B ? 1 : 0);
        return registersAfter;
    }

    //    gtrr (greater-than register/register) sets register C to 1 if register A is greater than register B. Otherwise, register C is set to 0.
    private static List<Integer> gtrr(int A, int B, int C, List<Integer> registersBefore) {
        List<Integer> registersAfter = new ArrayList<>(registersBefore);
        registersAfter.set(C, registersBefore.get(A) > registersBefore.get(B) ? 1 : 0);
        return registersAfter;
    }

    //    eqir (equal immediate/register) sets register C to 1 if value A is equal to register B. Otherwise, register C is set to 0.
    private static List<Integer> eqir(int A, int B, int C, List<Integer> registersBefore) {
        List<Integer> registersAfter = new ArrayList<>(registersBefore);
        registersAfter.set(C, A == registersBefore.get(B) ? 1 : 0);
        return registersAfter;
    }

    //    eqri (equal register/immediate) sets register C to 1 if register A is equal to value B. Otherwise, register C is set to 0.
    private static List<Integer> eqri(int A, int B, int C, List<Integer> registersBefore) {
        List<Integer> registersAfter = new ArrayList<>(registersBefore);
        registersAfter.set(C, registersBefore.get(A) == B ? 1 : 0);
        return registersAfter;
    }
    //    eqrr (equal register/register) sets register C to 1 if register A is equal to register B. Otherwise, register C is set to 0.
    private static List<Integer> eqrr(int A, int B, int C, List<Integer> registersBefore) {
        List<Integer> registersAfter = new ArrayList<>(registersBefore);
        registersAfter.set(C, registersBefore.get(A) == registersBefore.get(B) ? 1 : 0);
        return registersAfter;
    }
}
