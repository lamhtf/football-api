package page.lamht.football.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import page.lamht.football.entity.Match;
import page.lamht.football.mo.LastMatchMo;

import java.util.List;

@Mapper
public interface LastMatchMapper {

    LastMatchMapper INSTANCE = Mappers.getMapper(LastMatchMapper.class);

    @Mapping(source = "homeTeamId", target = "homeTeam.id")
    @Mapping(source = "homeTeamName", target = "homeTeam.name")
    @Mapping(source = "awayTeamId", target = "awayTeam.id")
    @Mapping(source = "awayTeamName", target = "awayTeam.name")
    @Mapping(source = "fullTimeHomeTeam", target = "homeTeam.score")
    @Mapping(source = "fullTimeAwayTeam", target = "awayTeam.score")
    @Mapping(source = "homeTeamCrestUrl", target = "homeTeam.crestUrl")
    @Mapping(source = "awayTeamCrestUrl", target = "awayTeam.crestUrl")
    LastMatchMo matchToLastMatchMo(Match match);

    List<LastMatchMo> matchsToLastMatchMos(List<Match> matchs);
}