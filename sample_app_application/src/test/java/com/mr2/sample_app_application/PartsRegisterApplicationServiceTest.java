package com.mr2.sample_app_application;

import com.mr2.sample_app_application.parts_register.PartsRegisterApplicationService;
import com.mr2.sample_app_domain.parts.Parts;
import com.mr2.sample_app_domain.parts.PartsRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.mock;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
//import static org.mockito.Mockito.when;

public class PartsRegisterApplicationServiceTest {
    private static final int testId = 1;
    private PartsRegisterApplicationService service;
    @Mock
    private PartsRepository stubRepo;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
//        PartsRepository stub = Mockito.mock(PartsRepository.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void stubSample(){
        // テスト対象のメソッドのロジックのみをテストするために、メソッド内で依存しているオブジェクトを
        // 「決まった返答しかしないダミー」に置き換える。このダミーを「スタブ」と呼ぶ。
        // TODO stubは分かったけどmockとspyの使い方が全然わからん
        Parts stubParts = Mockito.mock(Parts.class);
        Mockito.when(stubParts.getName()).thenReturn("testItem");

        Mockito.when(stubRepo.get(testId)).thenReturn(java.util.Optional.of(stubParts));
        Mockito.when(stubRepo.get(testId + 1)).thenReturn(Optional.empty());
        service = new PartsRegisterApplicationService(stubRepo);
        assertEquals(service.mockTest(testId), "name: testItem");
        assertEquals(service.mockTest(testId + 1), "missing item");
    }

    @Test
    public void registerSampleData() {
        //void
    }

    @Test
    public void registerNewParts() {
        //void
    }
}