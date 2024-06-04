package mos.contents.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mos.common.BaseTimeEntity;
import mos.mogako.entity.Mogako;

@Entity
@Getter
@RequiredArgsConstructor
public class SavedFile extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Mogako mogako;

    private String filename;
    private String url;

    public SavedFile(Mogako mogako, String filename, String url) {
       this.mogako = mogako;
       this.filename = filename;
       this.url = url;
    }

    public static SavedFile createNewFile(Mogako mogako, String filename, String url)
    {
        return new SavedFile(mogako, filename, url);
    }
}
