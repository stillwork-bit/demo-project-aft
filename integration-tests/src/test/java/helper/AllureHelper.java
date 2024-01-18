package helper;

import com.google.common.collect.ImmutableList;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.model.Parameter;
import io.qameta.allure.model.StepResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@UtilityClass
public class AllureHelper {
    private final AllureLifecycle lifecycle = Allure.getLifecycle();

    public Builder getAllureHelper() {
        return new Builder();
    }

    @Getter
    @AllArgsConstructor
    public enum ContentType {
        JSON("json", "application/json"),
        XML("xml", "application/xml");

        private final String fileExtension;
        private final String type;
    }

    public static class Builder {

        public Builder setCaseName(String caseName) {
            lifecycle.updateTestCase(
                    testResult -> testResult.setName(caseName));

            return this;
        }

        public Builder formatStepName(Object part, Object... parts) {
            var params = new ArrayList<>();
            params.add(part);
            params.addAll(List.of(parts));
            lifecycle.updateStep(stepResult ->
                    stepResult.setName(String.format(stepResult.getName(), params.toArray())));

            return this;
        }
        public Builder clearSteps() {
            lifecycle.updateStep(stepResult -> {
                List<StepResult> stepResults = stepResult.getSteps();
                stepResult.setSteps(ImmutableList.of(stepResults.get(stepResults.size() - 1)));
            });

            return this;
        }

        public Builder addParameter(@NonNull String name, Object value) {
            return addParameter(name, value, true);
        }

        public Builder addParameter(@NonNull String name, Object value, boolean addNull) {
            if (nonNull(value) || addNull) {
                final String paramValue = nonNull(value) ? value.toString() : "null";
                lifecycle.updateStep(stepResult ->
                        stepResult.getParameters().add(new Parameter().setName(name).setValue(paramValue)));
            }
            return this;
        }

        public Builder addParameters(List<Parameter> parameters) {
            lifecycle.updateStep(stepResult ->
                    stepResult.getParameters().addAll(parameters));

            return this;
        }

        public Builder maskParameter(String mask) {
            lifecycle.updateStep(stepResult -> stepResult.setParameters(
                    stepResult.getParameters().stream()
                            .map(s -> s.setValue(s.getValue().replace(mask, "***")))
                            .collect(Collectors.toList())
            ));

            return this;
        }

        public Builder clearTestParameters() {
            lifecycle.updateTestCase(testResult ->
                    testResult.setParameters(Collections.emptyList()));

            return this;
        }public Builder clearStepParameters() {
            lifecycle.updateTestCase(stepResult ->
                    stepResult.setParameters(Collections.emptyList()));

            return this;
        }

        public Builder addActualExpectedAttachments(@Nullable Object actual, @Nullable Object expected) {
            return addAttachment(actual, "actual", ContentType.JSON)
                    .addAttachment(expected, "expected", ContentType.JSON);
        }


        public Builder addAttachment(@Nullable Object o, @NonNull String name, @NonNull AllureHelper.ContentType contentType) {
                byte[] bytes = Objects.requireNonNull(o).toString().getBytes();
                lifecycle.addAttachment(name, contentType.getType(), contentType.getFileExtension(), bytes);

            return this;
        }
    }
}