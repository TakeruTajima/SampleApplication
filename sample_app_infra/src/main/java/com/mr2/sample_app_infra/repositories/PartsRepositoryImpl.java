package com.mr2.sample_app_infra.repositories;

import androidx.annotation.NonNull;

import com.mr2.sample_app_domain.common.Price;
import com.mr2.sample_app_domain.parts.Parts;
import com.mr2.sample_app_domain.parts.PartsRepository;
import com.mr2.sample_app_infra.room_database.MyDatabase;
import com.mr2.sample_app_infra.room_database.parts.PartsEntity;

import java.util.List;
import java.util.Optional;

public class PartsRepositoryImpl implements PartsRepository {
    private MyDatabase db;

    public PartsRepositoryImpl(@NonNull MyDatabase db) {
        this.db = db;
    }

    @Override
    public void store(Parts parts) {
        // 登録済みデータを検索
        List<PartsEntity> entityList = db.partsDao().find(parts.id());
        // 未登録ならInsert
        if (null == entityList || 1 != entityList.size()){
            db.partsDao().insert(toRoomEntity(parts));
            return;
        }
        // データバージョンが合わなければ楽観的排他処理で引っ掛かっているので要再試行
        PartsEntity entity = entityList.get(0);
        if (entity.version != parts.version()) throw new IllegalStateException("データが更新されています。再試行してください。");
        // アップデート実行
//        db.partsDao().update(toRoomEntity(parts));
        db.partsDao().update(
                parts.version(),
                parts.id(),
                parts.getName(),
                parts.getModel(),
                parts.getMaker(),
                parts.getValue().value,
                parts.getValue().currency,
                parts.getUnit()
        );
    }

    @Override
    public Optional<Parts> get(int id) {
        List<PartsEntity> entity = db.partsDao().find(id);
        if (null == entity || 1 != entity.size()) return Optional.empty();
        return Optional.of(toAggregate(entity.get(0)));
    }

    @Override
    public void remove(Parts parts) {
        db.partsDao().delete(toRoomEntity(parts));
    }

    @Override
    public void removeAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Parts> find(String maker, String model) {
        List<PartsEntity> entity = db.partsDao().find(maker, model);
        if (null == entity || 1 != entity.size() ) return Optional.empty();
        return Optional.of(toAggregate(entity.get(0)));
    }

    public static PartsEntity toRoomEntity(Parts agg){
        return new PartsEntity(
                agg.version(),
                agg.id(),
                agg.getName(),
                agg.getModel(),
                agg.getMaker(),
                agg.getValue().value,
                agg.getValue().currency,
                agg.getUnit());
    }

    public static Parts toAggregate(PartsEntity entity){
        return new Parts(
                entity.version,
                entity.id,
                entity.name,
                entity.model,
                entity.maker,
                new Price(entity.price_value, entity.price_currency),
                entity.unit);
    }
}
