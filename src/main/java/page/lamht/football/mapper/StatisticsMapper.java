package page.lamht.football.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import page.lamht.football.entity.Scorer;
import page.lamht.football.mo.ScorerMo;

import java.util.List;

@Mapper
public interface StatisticsMapper {
    StatisticsMapper INSTANCE = Mappers.getMapper(StatisticsMapper.class);

//    @Mapping(source = "team.shortName", target = "team.name")
    ScorerMo scorerToScorerMo(Scorer scorer);

    List<ScorerMo> scorersToScorerMos(List<Scorer> scorers);
}
