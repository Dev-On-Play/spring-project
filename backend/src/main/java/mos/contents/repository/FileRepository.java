package mos.contents.repository;

import mos.contents.entity.SavedFile;
import mos.mogako.entity.Mogako;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<SavedFile, Long> {
    Page<SavedFile> findAllByMogako (Mogako mogako, Pageable pageable);
}
