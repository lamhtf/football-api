package page.lamht.football.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import page.lamht.football.entity.Match;
import page.lamht.football.entity.Season;
import page.lamht.football.mo.MatchMo;
import page.lamht.football.mo.SeasonMo;

import java.util.List;

@Mapper
public interface MatchMapper {

    MatchMapper INSTANCE = Mappers.getMapper( MatchMapper.class );

    List<MatchMo> matchsToMatchMos(List<Match> matchs);

    @Mapping(source = "winner", target = "score.winner")
    @Mapping(source = "duration", target = "score.duration")
    @Mapping(source = "last_updated", target = "lastUpdated")
    @Mapping(source = "fullTimeHomeTeam", target = "score.fullTime.homeTeam")
    @Mapping(source = "fullTimeAwayTeam", target = "score.fullTime.awayTeam")
    @Mapping(source = "halfTimeHomeTeam", target = "score.halfTime.homeTeam")
    @Mapping(source = "halfTimeAwayTeam", target = "score.halfTime.awayTeam")
    @Mapping(source = "extraTimeHomeTeam", target = "score.extraTime.homeTeam")
    @Mapping(source = "extraTimeAwayTeam", target = "score.extraTime.awayTeam")
    @Mapping(source = "penaltiesHomeTeam", target = "score.penalties.homeTeam")
    @Mapping(source = "penaltiesAwayTeam", target = "score.penalties.awayTeam")
    @Mapping(source = "homeTeamId", target = "homeTeam.id")
    @Mapping(source = "homeTeamName", target = "homeTeam.name")
    @Mapping(source = "awayTeamId", target = "awayTeam.id")
    @Mapping(source = "awayTeamName", target = "awayTeam.name")
    MatchMo matchToMatchMo(Match match);

    SeasonMo seasonToSeasonMo(Season season);
}