package com.ljw.importbena;

import com.ljw.annotation.EnableDefinedBean;
import com.ljw.service.C;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.function.Predicate;

/**
 * @Description: todo
 * @Author: jianweil
 * @date: 2021/12/31 11:36
 */
public class ImportSelectorImpl implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {

        return new String[]{C.class.getName()};
        //return new String[]{"com.ljw.service.C"};
    }

    @Override
    public Predicate<String> getExclusionFilter() {
        return ImportSelector.super.getExclusionFilter();
    }
}
