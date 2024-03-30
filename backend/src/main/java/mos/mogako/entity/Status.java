package mos.mogako.entity;

public enum Status {
    CANCEL("일정 취소 상태"),
    RECRUITING("일정 모집 중 상태, 일정 생성 직후의 상태"),
    RECRUITING_COMPLETE("모임 시작시간 기준으로 마감, 이후부터는 출첵기능으로 체크"),
    ENDED("일정 정상 종료 상태");

    private final String description;

    Status(String description) {
        this.description = description;
    }
}
