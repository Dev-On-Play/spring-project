package mos.mogako.repository;

import mos.mogako.entity.Mogako;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MogakoRepositoryCustom {

    Page<Mogako> findAllWithFiltering(List<Long> categoryIds, List<Long> hashtagIds, Pageable pageable);
}
