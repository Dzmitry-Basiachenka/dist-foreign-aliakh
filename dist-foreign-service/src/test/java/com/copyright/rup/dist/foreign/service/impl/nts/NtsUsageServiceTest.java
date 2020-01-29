package com.copyright.rup.dist.foreign.service.impl.nts;

import static org.easymock.EasyMock.createMock;

import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.repository.api.INtsUsageRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

/**
 * Verifies {@link NtsUsageService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/28/2020
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RupContextUtils.class})
@SuppressWarnings("all") // TODO {aliakh} add implementation and remove @SuppressWarnings
@Ignore // TODO {aliakh} add methods and remove @Ignore
public class NtsUsageServiceTest {

    private final NtsUsageService ntsUsageService = new NtsUsageService();

    private INtsUsageRepository ntsUsageRepository;

    @Before
    public void setUp() {
        ntsUsageRepository = createMock(INtsUsageRepository.class);
        Whitebox.setInternalState(ntsUsageService, ntsUsageRepository);
    }
}
