package mos.mogako.repository;

import static mos.hashtag.entity.QMogakoHashtag.mogakoHashtag;
import static mos.mogako.entity.QMogako.mogako;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import mos.mogako.entity.Mogako;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class MogakoRepositoryImpl implements MogakoRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    /*
     * 카테고리는 하나만 포함되어도 됨
     * 해시태그는 전부 포함되어야 함
     *
     * "select m "
     * "from Mogako m "
     * "where m.id in (select mh.mogako.id "
     *                  "from MogakoHashtag mh "
     *                  "where mh.hashtag.id in :hashtagIds "
     *                  "group by mh.mogako.id "
     *                  "having count(distinct mh.hashtag.id) = :hashtagIdsSize) "
     * "and m.category.id in :categoryIds"
     */
    @Override
    public Page<Mogako> findAllWithFiltering(List<Long> categoryIds, List<Long> hashtagIds, Pageable pageable) {
        List<Mogako> content = queryFactory
                .selectFrom(mogako)
                .where(containsAnyCategory(categoryIds),
                        containsAllHashtag(hashtagIds)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(mogako.count())
                .from(mogako)
                .where(containsAnyCategory(categoryIds),
                        containsAllHashtag(hashtagIds)
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    private static BooleanExpression containsAnyCategory(List<Long> categoryIds) {
        if (categoryIds == null) {
            return null;
        }
        return mogako.category.id.in(categoryIds);
    }

    private static BooleanExpression containsAllHashtag(List<Long> hashtagIds) {
        if (hashtagIds == null) {
            return null;
        }
        return mogako.id.in(
                JPAExpressions.select(mogakoHashtag.mogako.id)
                        .from(mogakoHashtag)
                        .where(mogakoHashtag.hashtag.id.in(hashtagIds))
                        .groupBy(mogakoHashtag.mogako.id)
                        .having(mogakoHashtag.hashtag.id.countDistinct().eq((long) hashtagIds.size())));
    }
}
