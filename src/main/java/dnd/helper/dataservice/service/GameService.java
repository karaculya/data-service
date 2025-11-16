package dnd.helper.dataservice.service;

import dnd.helper.dataservice.exception.ConflictException;
import dnd.helper.dataservice.exception.FullnessException;
import dnd.helper.dataservice.exception.NotFoundException;
import dnd.helper.dataservice.model.entity.GameEntity;
import dnd.helper.dataservice.model.entity.UserEntity;
import dnd.helper.dataservice.model.mapper.GameMapper;
import dnd.helper.dataservice.repository.GameRepository;
import dnd.helper.dataservice.repository.UserRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import openapi.dto.Game;
import openapi.dto.ModifyGame;
import openapi.dto.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final GameMapper mapper;

    public Game createGame(Game game) {
        UserEntity master = userRepository.findById(game.getMasterId())
                .orElseThrow(() -> new NotFoundException("Мастер с id " + game.getMasterId() + " не найден"));
        GameEntity gameEntity = new GameEntity();
        Long id = gameEntity.getId();
        gameEntity = mapper.toEntity(game);
        gameEntity.setId(id);
        gameEntity.setMaster(master);

        if (gameEntity.getStatus() == null) gameEntity.setStatus(Status.PLANNED);

        if (gameEntity.getMaxPlayers() == null) gameEntity.setMaxPlayers(4);

        GameEntity savedGame = gameRepository.save(gameEntity);
        return mapper.toDto(savedGame);
    }

    public void deleteGame(Long id) {
        if (!gameRepository.existsById(id))
            throw new NotFoundException("Игра с id " + id + " не найдена");
        gameRepository.deleteById(id);
    }

    public Game getGame(Long id) {
        GameEntity gameEntity = gameRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Игра с id " + id + " не найдена"));
        return mapper.toDto(gameEntity);
    }

    public List<Game> getGames(Integer page, Integer limit, String status, Long masterId, Boolean isPublic) {
        Pageable pageable = PageRequest.of(
                page != null ? page : 0,
                limit != null ? limit : 20
        );

        Specification<GameEntity> spec = buildSpecification(status, masterId, isPublic);

        Page<GameEntity> gamePage = gameRepository.findAll(spec, pageable);
        return gamePage.getContent()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    private Specification<GameEntity> buildSpecification(String status, Long masterId, Boolean isPublic) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (status != null)
                predicates.add(criteriaBuilder.equal(root.get("status"), Status.fromValue(status)));
            if (masterId != null)
                predicates.add(criteriaBuilder.equal(root.get("master").get("id"), masterId));
            if (isPublic != null)
                predicates.add(criteriaBuilder.equal(root.get("isPublic"), isPublic));
            if (predicates.isEmpty())
                return criteriaBuilder.conjunction();

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
    public void joinGame(Long gameId, Long personId) {
        GameEntity game = gameRepository.findById(gameId)
                .orElseThrow(() -> new NotFoundException("Игра с id " + gameId + " не найдена"));

        UserEntity user = userRepository.findById(personId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + personId + " не найдена"));

        if (game.getPlayers().stream().anyMatch(player -> player.getId().equals(personId)))
            throw new ConflictException("Пользователь уже участник этой игры");
        if (game.getMaster().getId().equals(personId))
            throw new ConflictException("Мастер игры не может быть ее игроком");
        if (game.getPlayers().size() >= game.getMaxPlayers())
            throw new FullnessException("В этой игре уже максимум игроков");
        if (game.getStatus() != Status.PLANNED && game.getStatus() != Status.ACTIVE)
            throw new ConflictException("Нельзя присоединиться к игре в статусе - " + game.getStatus());

        game.getPlayers().add(user);
        gameRepository.save(game);
    }

    public Game updateGame(Long id, ModifyGame modifyGame) {
        GameEntity gameEntity = gameRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Игра с id " + id + " не найдена"));

        if (modifyGame.getName() != null) gameEntity.setName(modifyGame.getName());
        if (modifyGame.getDescription() != null) gameEntity.setDescription(modifyGame.getDescription());
        if (modifyGame.getIsPublic() != null) gameEntity.setIsPublic(modifyGame.getIsPublic());
        if (modifyGame.getMaxPlayers() != null) gameEntity.setMaxPlayers(modifyGame.getMaxPlayers());
        if (modifyGame.getStatus() != null) gameEntity.setStatus(modifyGame.getStatus());
        if (modifyGame.getNextSession() != null) gameEntity.setNextSession(modifyGame.getNextSession().toLocalDateTime());

        GameEntity updatedGame = gameRepository.save(gameEntity);
        return mapper.toDto(updatedGame);
    }
}
