package com.zhuk.beautyshop.config;

import com.zhuk.beautyshop.domain.entity.FavourTranslation;
import com.zhuk.beautyshop.domain.model.FavourTranslationModel;
import com.zhuk.beautyshop.dto.FavourDto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class OrikaMapperConfig extends ConfigurableMapper {
    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(FavourTranslationModel.class, FavourDto.class)
                .field("id", "id")
                .field("favour.price", "price")
                .field("favour.category", "category")
                .field("language", "language")
                .field("text", "title")
                .register();

        factory.classMap(FavourTranslation.class, FavourTranslationModel.class).byDefault().register();

    }
}
