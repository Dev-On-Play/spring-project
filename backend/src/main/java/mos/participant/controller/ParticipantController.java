package mos.participant.controller;

import lombok.RequiredArgsConstructor;
import mos.auth.domain.Authenticated;
import mos.participant.dto.ParticipantResponse;
import mos.participant.service.ParticipantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantService participantService;

    @PostMapping("/api/mogakos/{mogakoId}/participate")
    public ResponseEntity<Void> participate(@Authenticated Long authMemberId, @PathVariable Long mogakoId) {
        Long participantId = participantService.participate(authMemberId, mogakoId);
        return ResponseEntity.created(URI.create("/api/participants/" + participantId)).build();
    }

    @GetMapping("/api/mogakos/{mogakoId}/participants")
    public ResponseEntity<List<ParticipantResponse>> findParticipants(@PathVariable Long mogakoId) {
        return ResponseEntity.ok(participantService.findParticipants(mogakoId));
    }
}
