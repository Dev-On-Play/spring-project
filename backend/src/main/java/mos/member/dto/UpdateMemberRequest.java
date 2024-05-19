package mos.member.dto;

public record UpdateMemberRequest(String nickname, String email,
                                  String introduction, String profile) {
}
