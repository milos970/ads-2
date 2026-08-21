package com.milos970.model.service;

import com.milos970.model.CreatePatient;
import com.milos970.model.entity.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public final class Generator
{
    private final Random random;

    private static final int MAX_NUMBER_OF_REGIONS = 1000;
    private static final int MAX_NUMBER_OF_DISTRICTS = 10000;
    private static final int MAX_NUMBER_OF_WORKPLACES = 50_0000;
    private static final int MAX_NUMBER_OF_PATIENTS = 100_0000;
    private static final int MAX_NUMBER_OF_PCR_TESTS = 1_000_0000;

    private int numberOfRegions;
    private int numberOfDistricts;
    private int numberOfWorkplaces;
    private int numberOfPatients;
    private int numberOfTests;

    private final BasicOperations operations;


    public Generator(int numOfRegions, int numberOfDistricts, int numberOfWorkplaces, int numberOfPatients, int numberOfTests,
                     BasicOperations operations) {
        if (numOfRegions > MAX_NUMBER_OF_REGIONS
                || numberOfDistricts > MAX_NUMBER_OF_DISTRICTS
                || numberOfWorkplaces > MAX_NUMBER_OF_WORKPLACES
                || numberOfPatients > MAX_NUMBER_OF_PATIENTS
                || numberOfTests > MAX_NUMBER_OF_PCR_TESTS) {
            throw new IllegalArgumentException();

        }

        if (numOfRegions > numberOfDistricts) {
            throw new IllegalArgumentException("Počet krajov nemôže byť väčší ako počet okresov.");
        }

        if (numberOfDistricts > numberOfWorkplaces) {
            throw new IllegalArgumentException("Počet okresov nemôže byť väčší ako počet pracovísk.");
        }

        this.numberOfRegions = numOfRegions;
        this.numberOfDistricts = numberOfDistricts;
        this.numberOfWorkplaces = numberOfWorkplaces;
        this.numberOfPatients = numberOfPatients;
        this.numberOfTests = numberOfTests;

        this.random = new Random();

        this.operations = operations;
    }

    public void generateAll()
    {
        Region[] regions = new Region[numberOfRegions];
        Arrays.setAll(regions, i -> new Region(i + 1));

        District[] districts = new District[numberOfDistricts];
        Arrays.setAll(districts, i -> new District((i + 1) * numberOfDistricts));

        Workplace[] workplaces = new Workplace[numberOfWorkplaces];
        Arrays.setAll(workplaces, i -> new Workplace((i + 1) * numberOfWorkplaces));

        Patient[] patients = new Patient[numberOfPatients];
        Arrays.setAll(patients, i -> generatePatient());

        Test[] tests = new Test[numberOfTests];
        for (int i = 0; i < numberOfDistricts; ++i)
        {
            regions[this.random.nextInt(regions.length)].getDistricts().insert(districts[i].id(),districts[i]);
        }

        for (int i = 0; i < numberOfWorkplaces; ++i)
        {
            districts[this.random.nextInt(districts.length)].getWorkplaces().insert(workplaces[i].id(),workplaces[i]);
        }

        for (int i = 0; i < numberOfTests; ++i)
        {
            var region = regions[this.random.nextInt(numberOfRegions)];
            var district = districts[this.random.nextInt(districts.length)];
            var workplace = workplaces[this.random.nextInt(workplaces.length)];
            Patient patient = patients[this.random.nextInt(patients.length)];
            var test = generatePCRTest(patient.id(),district.id(), region.id(), workplace.id());
            test.setPatient(patient);
            this.operations.createPatient(new CreatePatient(patient.id(), patient.name(), patient.surname(), patient.birthday()));
            this.operations.one(test.getDateTime(),test.getPatientId(), test.getId(), test.getDistrictId(), test.getRegionId(), test.getWorkplaceId(),test.getValue(), test.isResult(), test.getNote());
        }
    }

    private Test generatePCRTest(String idPatient, int idDistrict, int idRegion, int idWorkplace) {
        int idTest = random.nextInt(0, Integer.MAX_VALUE);
        double value = random.nextDouble();
        boolean result = value > 0.6 ? true : false;
        String note = result ? pozitivnePcrPopisy[this.random.nextInt(pozitivnePcrPopisy.length)] : negativnePcrPopisy[this.random.nextInt(negativnePcrPopisy.length)];
        return new Test(generateDateTime(),idPatient, idTest, idDistrict, idRegion, idWorkplace, result, value,note);
    }

    private String[] negativnePcrPopisy = {
            "PCR test je negatívny – vírus nebol zistený.",
            "Negatívny výsledok PCR testu.",
            "PCR test nepotvrdil prítomnosť vírusu.",
            "Vzorka je negatívna na sledovaný vírus.",
            "Nebola detegovaná vírusová RNA."
    };

    private String[] pozitivnePcrPopisy = {
            "PCR test je pozitívny – vírus bol zistený.",
            "Pozitívny výsledok PCR testu.",
            "PCR test potvrdil prítomnosť vírusu.",
            "Vzorka obsahuje vírusovú RNA.",
            "Bola zistená infekcia vírusom."
    };




    private  Patient generatePatient() {
        String[] menaMuz = {
                "Ján","Peter","Martin","Marek","Lukáš","Tomáš","Michal","Andrej","Filip","Adam",
                "Patrik","Róbert","Viktor","Juraj","Roman","Marián","Samuel","Pavol","Dominik","Erik",
                "Daniel","Richard","Igor","Stanislav","Dušan","Jozef","Karol","Peter","Milan","Štefan",
                "Boris","Vladimír","Radovan","Tibor","Gabriel","Alan","René","Adrián","Viliam","Rastislav"
        };

        String[] menaZeny = {
                "Lucia","Mária","Zuzana","Katarína","Veronika","Petra","Jana","Eva","Monika","Anna",
                "Kristína","Barbora","Simona","Lenka","Tatiana","Martina","Adriana","Natália","Bianka","Michaela",
                "Silvia","Andrea","Ivana","Diana","Nina","Dominika","Ela","Mária","Sabina","Tamara",
                "Viktória","Ema","Laura","Gabriela","Žaneta","Lýdia","Klaudia","Blažena","Karolína","Soňa"
        };

        String[] priezviska = {
                "Novák","Kováč","Horváth","Tóth","Varga","Kiss","Baláž","Szabó","Polák","Urban",
                "Král","Hudec","Chovanec","Hruška","Šimko","Kubiš","Kadlec","Farkaš","Dudáš","Marek",
                "Bartoš","Benko","Pašek","Doležal","Krajčí","Mach","Černák","Pekár","Žiak","Gregor",
                "Hollý","Holub","Švec","Moravčík","Kováčik","Krnáč","Vlach","Šimunek","Červeň","Blaško"
        };

        boolean isZena = random.nextDouble() < 0.5;

        String meno = isZena ? menaZeny[random.nextInt(menaZeny.length)] : menaMuz[random.nextInt(menaMuz.length)];
        String priezvisko = priezviska[random.nextInt(priezviska.length)];

        if (isZena && !priezvisko.endsWith("á")) {
            priezvisko += "ová";
        }

        LocalDate birthday = generateBirthday();
        return new Patient(generateRodCislo(birthday,isZena),meno,priezvisko,birthday);

    }

    private  LocalDate generateBirthday() {

        int year = 1955 + random.nextInt(66);

        int month = random.nextInt(12) + 1;

        int day = switch(month) {
            case 1,3,5,7,8,10,12 -> random.nextInt(31) + 1;
            case 4,6,9,11 -> random.nextInt(30) + 1;
            case 2 -> (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0) ? random.nextInt(29) + 1 : random.nextInt(28) + 1;
            default -> throw new IllegalStateException("Unexpected value: " + month);
        };

        return LocalDate.of(year, month,day);
    }

    private  LocalDateTime generateDateTime() {

        int year = 2020 + random.nextInt(6);

        int month = random.nextInt(1,13);

        int maxDays = switch (month) {
            case 1, 3, 5, 7, 8, 10, 12 -> 31;
            case 4, 6, 9, 11 -> 30;
            case 2 -> (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) ? 29 : 28;
            default -> throw new IllegalStateException("Unexpected month: " + month);
        };
        int day = random.nextInt(maxDays) + 1;

        int hours = random.nextInt(6,20);
        int minutes = random.nextInt(60);
        int seconds = random.nextInt(60);

        return LocalDateTime.of(year, month,day, hours, minutes, seconds);
    }


    private  String generateRodCislo(LocalDate birthday, boolean isZena) {
        int year = birthday.getYear() % 100;
        int month = birthday.getMonthValue();
        int day = birthday.getDayOfMonth();
        int poradie = random.nextInt(10_000);
        return String.format("%02d%02d%02d/%04d", year, month, day, poradie);
    }


}
