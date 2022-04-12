package hellojpa.jpashopp.domain.item;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Hunseong on 2022/04/12
 */
@Entity
@Getter
@DiscriminatorValue("A")
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성 메서드만으로 객체 생성. protected로 new keyword 통한 객체 생성X
public class Album extends Item {

    private String artist;
    private String etc;
}
