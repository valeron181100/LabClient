package FileSystem;

import Clothes.Costume;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import sun.invoke.empty.Empty;

import javax.xml.stream.XMLEventWriter;
import java.io.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Класс-менеджер коллекции.
 * @author Валерий Бондарь
 */
public class CollectionManager {
    /**
     * Получает коллекцию из XML строки.
     * @author Валерий Бондарь
     * @return HashSet\<Costume\>
     */
    public static HashSet<Costume> getCollectionFromXML(String xml) throws EmptyFileException{
        HashSet<Costume> costumes = new HashSet<>();

        if (xml.length() == 0) {
            throw new EmptyFileException();
        } else {
            JSONArray jsonArray;
            jsonArray = XML.toJSONObject(xml).getJSONArray("array");
            jsonArray.forEach(p -> costumes.add(new Costume((JSONObject) p)));
        }

        return costumes;
    }

    public static byte[] getBytesFromCollection(HashSet<Costume> costumes){
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos)){
            oos.writeObject(costumes);
            return baos.toByteArray();
        } catch (IOException e) {
            System.err.println("Ошибка: не удалось сериализовать коллекцию");
        }
        return null;
    }

    /**
     * Получает XML строку из коллекции.
     * @author Валерий Бондарь
     * @return HashSet\<Costume\>
     */
    public static String getXmlFromCollection(HashSet<Costume> collection){
        JSONArray array = new JSONArray();
        collection.forEach(p -> array.put(p.getJson()));
        return XML.toString(array);
    }

    public static String collectionStringXML = "<array>\n" +
            "    <topClothes>\n" +
            "        <growth_sm>170</growth_sm>\n" +
            "        <size>60</size>\n" +
            "        <color>Red</color>\n" +
            "        <material>Chlopoc</material>\n" +
            "        <is_hood>true</is_hood>\n" +
            "        <name>T-Shirt</name>\n" +
            "        <is_for_man>true</is_for_man>\n" +
            "        <hand_sm_length>60</hand_sm_length>\n" +
            "    </topClothes>\n" +
            "    <downClothes>\n" +
            "        <size>50</size>\n" +
            "        <color>White</color>\n" +
            "        <material>Chlopoc</material>\n" +
            "        <diametr_leg_sm>40</diametr_leg_sm>\n" +
            "        <name>Trousers</name>\n" +
            "        <leg_length_sm>30</leg_length_sm>\n" +
            "        <is_for_man>true</is_for_man>\n" +
            "    </downClothes>\n" +
            "    <underwear>\n" +
            "        <sex_lvl>20</sex_lvl>\n" +
            "        <size>25</size>\n" +
            "        <color>Black</color>\n" +
            "        <material>Chlopoc</material>\n" +
            "        <name>Panties</name>\n" +
            "        <is_for_man>true</is_for_man>\n" +
            "    </underwear>\n" +
            "    <hat>\n" +
            "        <cylinder_height_sm>15</cylinder_height_sm>\n" +
            "        <size>50</size>\n" +
            "        <color>White</color>\n" +
            "        <material>Len</material>\n" +
            "        <visor_length_sm>20</visor_length_sm>\n" +
            "        <name>BaseballHat</name>\n" +
            "        <is_for_man>true</is_for_man>\n" +
            "    </hat>\n" +
            "    <shoes>\n" +
            "        <is_shoelaces>true</is_shoelaces>\n" +
            "        <size>38</size>\n" +
            "        <color>White</color>\n" +
            "        <material>Leather</material>\n" +
            "        <outsole_material>Rubber</outsole_material>\n" +
            "        <name>Sneackers</name>\n" +
            "        <is_for_man>true</is_for_man>\n" +
            "    </shoes>\n" +
            "</array>\n" +
            "<array>\n" +
            "<topClothes>\n" +
            "    <growth_sm>170</growth_sm>\n" +
            "    <size>50</size>\n" +
            "    <color>Gray</color>\n" +
            "    <material>Chlopoc</material>\n" +
            "    <is_hood>false</is_hood>\n" +
            "    <name>T-Shirt</name>\n" +
            "    <is_for_man>true</is_for_man>\n" +
            "    <hand_sm_length>60</hand_sm_length>\n" +
            "</topClothes>\n" +
            "<downClothes>\n" +
            "    <size>50</size>\n" +
            "    <color>Black</color>\n" +
            "    <material>Chlopoc</material>\n" +
            "    <diametr_leg_sm>40</diametr_leg_sm>\n" +
            "    <name>Pants</name>\n" +
            "    <leg_length_sm>80</leg_length_sm>\n" +
            "    <is_for_man>true</is_for_man>\n" +
            "</downClothes>\n" +
            "<underwear>\n" +
            "    <sex_lvl>100</sex_lvl>\n" +
            "    <size>30</size>\n" +
            "    <color>Black</color>\n" +
            "    <material>Chlopoc</material>\n" +
            "    <name>Panties</name>\n" +
            "    <is_for_man>true</is_for_man>\n" +
            "</underwear>\n" +
            "<hat>\n" +
            "    <cylinder_height_sm>15</cylinder_height_sm>\n" +
            "    <size>50</size>\n" +
            "    <color>Black</color>\n" +
            "    <material>Len</material>\n" +
            "    <visor_length_sm>20</visor_length_sm>\n" +
            "    <name>BaseballHat</name>\n" +
            "    <is_for_man>true</is_for_man>\n" +
            "</hat>\n" +
            "<shoes>\n" +
            "    <is_shoelaces>true</is_shoelaces>\n" +
            "    <size>38</size>\n" +
            "    <color>Brown</color>\n" +
            "    <material>Leather</material>\n" +
            "    <outsole_material>Rubber</outsole_material>\n" +
            "    <name>Boots</name>\n" +
            "    <is_for_man>true</is_for_man>\n" +
            "</shoes>\n" +
            "</array>";
}

