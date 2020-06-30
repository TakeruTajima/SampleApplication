package com.mr2.sample_app_domain.common;

public abstract class AbstractEntity extends AssertionConcern{
    protected static final int INITIAL_VERSION = 0;
    protected static final int INITIAL_ID = 0;

    protected AbstractEntity(int version, int id) {
        this.version = version;
        this.id = id;
    }

    /**
     *  エンティティのデータバージョン
     */
    protected final int version;
    public int version(){ return version; }

    /**
     *  エンティティのID
     */
    protected final int id;
    public int id(){ return id; }

    // 同一性チェック
    protected boolean sameIdentityAs(Object o){
        if (null == o || this.getClass() != o.getClass()) return false;
        return ((AbstractEntity)o).id == this.id;
    }

    //等価性チェックはどうすっぺ？？？
}
