package day16;


import day4.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


public class Main {

    private static final List<Function<Integer>> OPERATIONS = Arrays.asList(
            Main::addi,
            Main::addr,
            Main::muli,
            Main::mulr,
            Main::bani,
            Main::banr,
            Main::bori,
            Main::borr,
            Main::gtir,
            Main::gtri,
            Main::gtrr,
            Main::eqir,
            Main::eqri,
            Main::eqrr,
            Main::seti,
            Main::setr
    );

    public static void main(String[] args) throws IOException {
        String fileNameTest = "src/main/resources/day16/input_test.txt";
        String fileName = "src/main/resources/day16/input.txt";

        System.out.println(part1(fileNameTest));
        System.out.println(part1(fileName));

        System.out.println(part2(fileName));

    }

    private static int part1(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        int i = 0;
        int samples = 0;

        while(i < lines.size() && lines.get(i).startsWith("Before") && lines.get(i + 2).startsWith("After")) {
            List<Integer> registersBefore = getRegisters(lines, i, "Before: [".length());
            List<Integer> instruction = getInstruction(lines, i + 1);
            List<Integer> registersAfter = getRegisters(lines, i + 2, "After:  [".length());

            long count = OPERATIONS.stream()
                    .map(op -> op.apply(instruction.get(1), instruction.get(2), instruction.get(3), registersBefore))
                    .filter(result -> result.equals(registersAfter))
                    .count();

            samples += count >= 3 ? 1 : 0;
            i += 4;
        }

        return samples;
    }

    private static int part2(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        Map<Integer, Function<Integer>> opCodes = new HashMap<>();

        int i = 0;
        while(i < lines.size() && lines.get(i).startsWith("Before") && lines.get(i + 2).startsWith("After")) {
            List<Integer> registersBefore = getRegisters(lines, i, "Before: [".length());
            List<Integer> instruction = getInstruction(lines, i + 1);
            List<Integer> registersAfter = getRegisters(lines, i + 2, "After:  [".length());

            List<Pair<Function<Integer>, List<Integer>>> count = OPERATIONS.stream()
                    .filter(op -> !opCodes.containsValue(op))
                    .map(op -> new Pair<>(op, op.apply(instruction.get(1), instruction.get(2), instruction.get(3), registersBefore)))
                    .filter(result -> result.getValue().equals(registersAfter))
                    .collect(Collectors.toList());

            if (count.size() == 1) {
                opCodes.putIfAbsent(instruction.get(0), count.get(0).getKey());
            }

            i += 4;
        }
        i += 2;


        List<Integer> registers = Arrays.asList(0, 0, 0, 0);
        while (i < lines.size()) {
            List<Integer> instruction = getInstruction(lines, i);
            registers = opCodes.get(instruction.get(0)).apply(instruction.get(1), instruction.get(2), instruction.get(3), registers);
            i++;
        }

        return registers.get(0);
    }

    private static List<Integer> getInstruction(List<String> lines, int i) {
        return Arrays.stream(lines.get(i).split(" "))
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
