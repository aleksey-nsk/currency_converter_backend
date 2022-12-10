package com.example.demo.util;

import lombok.Getter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlRootElement(name = "ValCurs")
@Getter
public class ValCurs {

    @XmlElement(name = "Valute")
    public List<Valute> valutes;

    @XmlType
    public static class Valute {

        @XmlAttribute(name = "ID")
        public String id;

        @XmlElement(name = "NumCode")
        public String numCode;

        @XmlElement(name = "CharCode")
        public String charCode;

        @XmlElement(name = "Nominal")
        public Integer nominal;

        @XmlElement(name = "Name")
        public String name;

        @XmlElement(name = "Value")
        public String value;

        @Override
        public String toString() {
            return "Valute{" +
                    "id='" + id + '\'' +
                    ", numCode='" + numCode + '\'' +
                    ", charCode='" + charCode + '\'' +
                    ", nominal=" + nominal +
                    ", name='" + name + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ValCurs{" +
                "valutes=" + valutes +
                '}';
    }
}
