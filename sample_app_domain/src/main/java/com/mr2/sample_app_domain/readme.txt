domain
    運用はDDDに準拠する。

Entityについて

同一性と同値性の比較

AbstractEntity.sameIdentityAs(Object o)
    同一性の比較に使用。
    idを比較し同値ならtrueを返す。

Equals(Object o)
    idを含むすべてのメンバをObjects.equals()で比較。

不変条件のチェック
    UI層でinvariantメソッドをコール。

invariantメソッド
