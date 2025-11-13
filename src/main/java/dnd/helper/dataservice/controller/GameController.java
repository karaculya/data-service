package dnd.helper.dataservice.controller;

import dnd.helper.dataservice.service.GameService;
import lombok.RequiredArgsConstructor;
import openapi.api.GamesApi;
import openapi.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GameController implements GamesApi {
    private final GameService service;

    @Override
    public ResponseEntity<Game> createGame(Game game) {
        return ResponseEntity.ok(service.createGame(game));
    }

    @Override
    public ResponseEntity<Void> deleteGame(Long id) {
        service.deleteGame(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Game> getGame(Long id) {
        return ResponseEntity.ok(service.getGame(id));
    }

    @Override
    public ResponseEntity<List<Game>> getGames(Integer page, Integer limit, String status, Long masterId, Boolean isPublic) {
        return ResponseEntity.ok(service.getGames(page, limit, status, masterId, isPublic));
    }

    @Override
    public ResponseEntity<Void> joinGame(Long gameId, Long personId) {
        service.joinGame(gameId, personId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Game> updateGame(Long id, ModifyGame modifyGame) {
        return ResponseEntity.ok(service.updateGame(id, modifyGame));
    }
}
