package com.milos970.repository;

import com.milos970.model.PCR;
import com.milos970.structure.BSTree;

import java.time.LocalDate;
import java.util.List;

public class PCRRepository implements Repository<PCR>
{

    public PCRRepository() {
        var regions = new BSTree<Integer, BSTree<Integer, String>>();

        regions.insert(101, new BSTree<>()); // Bratislavský kraj
        regions.insert(201, new BSTree<>()); // Trnavský kraj
        regions.insert(301, new BSTree<>()); // Trenčiansky kraj
        regions.insert(401, new BSTree<>()); // Nitriansky kraj
        regions.insert(501, new BSTree<>()); // Žilinský kraj
        regions.insert(601, new BSTree<>()); // Banskobystrický kraj
        regions.insert(701, new BSTree<>()); // Prešovský kraj
        regions.insert(801, new BSTree<>()); // Košický kraj

// Bratislavský kraj (101)
        var baRegion = new BSTree<Integer, String>();
        baRegion.insert(10101, "Bratislava I");
        baRegion.insert(10102, "Bratislava II");
        baRegion.insert(10103, "Bratislava III");
        baRegion.insert(10104, "Bratislava IV");
        baRegion.insert(10105, "Bratislava V");
        baRegion.insert(10106, "Malacky");
        baRegion.insert(10107, "Pezinok");
        baRegion.insert(10108, "Senec");
        regions.insert(101, baRegion);

// Trnavský kraj (201)
        var ttRegion = new BSTree<Integer, String>();
        ttRegion.insert(20101, "Dunajská Streda");
        ttRegion.insert(20102, "Galanta");
        ttRegion.insert(20103, "Hlohovec");
        ttRegion.insert(20104, "Piešťany");
        ttRegion.insert(20105, "Senica");
        ttRegion.insert(20106, "Skalica");
        ttRegion.insert(20107, "Trnava");
        regions.insert(201, ttRegion);

// Trenčiansky kraj (301)
        var tnRegion = new BSTree<Integer, String>();
        tnRegion.insert(30101, "Bánovce nad Bebravou");
        tnRegion.insert(30102, "Ilava");
        tnRegion.insert(30103, "Myjava");
        tnRegion.insert(30104, "Nové Mesto nad Váhom");
        tnRegion.insert(30105, "Partizánske");
        tnRegion.insert(30106, "Považská Bystrica");
        tnRegion.insert(30107, "Prievidza");
        tnRegion.insert(30108, "Púchov");
        tnRegion.insert(30109, "Trenčín");
        regions.insert(301, tnRegion);

// Nitriansky kraj (401)
        var nrRegion = new BSTree<Integer, String>();
        nrRegion.insert(40101, "Komárno");
        nrRegion.insert(40102, "Levice");
        nrRegion.insert(40103, "Nitra");
        nrRegion.insert(40104, "Nové Zámky");
        nrRegion.insert(40105, "Šaľa");
        nrRegion.insert(40106, "Topoľčany");
        nrRegion.insert(40107, "Zlaté Moravce");
        regions.insert(401, nrRegion);

// Žilinský kraj (501)
        var zaRegion = new BSTree<Integer, String>();
        zaRegion.insert(50101, "Bytča");
        zaRegion.insert(50102, "Čadca");
        zaRegion.insert(50103, "Dolný Kubín");
        zaRegion.insert(50104, "Kysucké Nové Mesto");
        zaRegion.insert(50105, "Liptovský Mikuláš");
        zaRegion.insert(50106, "Martin");
        zaRegion.insert(50107, "Námestovo");
        zaRegion.insert(50108, "Ružomberok");
        zaRegion.insert(50109, "Turčianske Teplice");
        zaRegion.insert(50110, "Tvrdošín");
        zaRegion.insert(50111, "Žilina");
        regions.insert(501, zaRegion);

// Banskobystrický kraj (601)
        var bbRegion = new BSTree<Integer, String>();
        bbRegion.insert(60101, "Banská Bystrica");
        bbRegion.insert(60102, "Brezno");
        bbRegion.insert(60103, "Detva");
        bbRegion.insert(60104, "Krupina");
        bbRegion.insert(60105, "Lučenec");
        bbRegion.insert(60106, "Poltár");
        bbRegion.insert(60107, "Revúca");
        bbRegion.insert(60108, "Rimavská Sobota");
        bbRegion.insert(60109, "Veľký Krtíš");
        bbRegion.insert(60110, "Zvolen");
        bbRegion.insert(60111, "Žarnovica");
        bbRegion.insert(60112, "Žiar nad Hronom");
        regions.insert(601, bbRegion);

// Prešovský kraj (701)
        var poRegion = new BSTree<Integer, String>();
        poRegion.insert(70101, "Bardejov");
        poRegion.insert(70102, "Humenné");
        poRegion.insert(70103, "Kežmarok");
        poRegion.insert(70104, "Levoča");
        poRegion.insert(70105, "Medzilaborce");
        poRegion.insert(70106, "Poprad");
        poRegion.insert(70107, "Prešov");
        poRegion.insert(70108, "Sabinov");
        poRegion.insert(70109, "Snina");
        poRegion.insert(70110, "Stará Ľubovňa");
        poRegion.insert(70111, "Stropkov");
        poRegion.insert(70112, "Svidník");
        poRegion.insert(70113, "Vranov nad Topľou");
        regions.insert(701, poRegion);

// Košický kraj (801)
        var keRegion = new BSTree<Integer, String>();
        keRegion.insert(80101, "Gelnica");
        keRegion.insert(80102, "Košice I");
        keRegion.insert(80103, "Košice II");
        keRegion.insert(80104, "Košice III");
        keRegion.insert(80105, "Košice IV");
        keRegion.insert(80106, "Košice-okolie");
        keRegion.insert(80107, "Michalovce");
        keRegion.insert(80108, "Rožňava");
        keRegion.insert(80109, "Sobrance");
        keRegion.insert(80110, "Spišská Nová Ves");
        keRegion.insert(80111, "Trebišov");
        regions.insert(801, keRegion);

    }

    @Override
    public void save(PCR entity) {

    }

    @Override
    public void removeById(int id) {

    }

    public List<PCR> findByPatientId(int id) {
        //3
        return null;
    }

    public List<PCR> findByIdWorkPlace(int id) {
        //3
        return null;
    }

    @Override
    public PCR findById(int id) {

        //hashMap(testy, strom)
        //operacie 1,
        return null;
    }

    @Override
    public List<PCR> findByDistrictId(int id) {
        //get district by id
        //iterate over his workplaces
        //find
        return null;
    }

    @Override
    public List<PCR> findByRegionId(int id) {
        //iterate over his districts
        return null;
    }

    @Override
    public List<PCR> findByDateBetween(LocalDate from, LocalDate to) {
        return null;
    }
}
