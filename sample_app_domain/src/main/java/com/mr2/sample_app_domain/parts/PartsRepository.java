package com.mr2.sample_app_domain.parts;

import java.util.Optional;

public interface PartsRepository {
    void store(Parts parts);
    Optional<Parts> get(int id);
    void remove(Parts parts);
    void removeAll();

    Optional<Parts> find(String maker, String model);
}

// 新しい集約をデータベースに追加する
// クライアント側からファクトリを呼び出して集約を生成します。
// そして、リポジトリの追加メソッドを呼び出すことで永続化の対象として登録します。
// これによってデータベースに集約の情報が挿入されます。
//
//　データベースから集約を取り出す
// リポジトリはデータベースにクエリを投げてデータを取り出します。
// そしてファクトリを使って集約のモデルを再構成し、クライアントに集約を戻します。
// 取り出した集約の変更内容はデータベースへ反映されます。
