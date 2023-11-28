package Modelo;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

import java.util.List;
@XmlRootElement(name="dungeon")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Dungeon {
    List<Room> room;

    public void setRoom(List<Room> room) {
        this.room = room;
    }
}
