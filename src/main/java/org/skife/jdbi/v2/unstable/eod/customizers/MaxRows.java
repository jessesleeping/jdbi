package org.skife.jdbi.v2.unstable.eod.customizers;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.StatementCustomizer;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Customizer(MaxRows.Factory.class)
public @interface MaxRows
{
    int value() default Integer.MAX_VALUE;

    static class Factory implements StatementCustomizerFactory
    {
        public StatementCustomizer createForParameter(Annotation annotation, Object arg)
        {
            final Integer va = (Integer) arg;
            return new StatementCustomizer() {

                public void beforeExecution(PreparedStatement stmt, StatementContext ctx) throws SQLException
                {
                    stmt.setMaxRows(va);
                }

                public void afterExecution(PreparedStatement stmt, StatementContext ctx) throws SQLException
                {
                }
            };
        }

        public StatementCustomizer createForMethod(Annotation annotation)
        {
            final MaxRows fs = (MaxRows) annotation;
            return new StatementCustomizer() {

                public void beforeExecution(PreparedStatement stmt, StatementContext ctx) throws SQLException
                {
                    stmt.setMaxRows(fs.value());
                }

                public void afterExecution(PreparedStatement stmt, StatementContext ctx) throws SQLException
                {
                }
            };
        }
    }


}