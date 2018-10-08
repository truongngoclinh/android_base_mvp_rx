package linhtruong.com.mershop.task;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import linhtruong.com.mershop.base.BaseTask;
import linhtruong.com.mershop.base.task.TaskResource;
import linhtruong.com.mershop.di.MockMode;
import linhtruong.com.mershop.network.http.service.MSTimelineService;
import linhtruong.com.protocol.gson.response.TimelineCategoryResponse;
import linhtruong.com.protocol.gson.response.TimelineUrlsResponse;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Get timeline json data
 *
 * @author linhtruong
 * @date 10/4/18 - 10:14.
 * @organization VED
 */
public class TaskGetTimeLineConfig extends BaseTask<HashMap<String, List<TimelineCategoryResponse.CategoryItem>>> {

    @Inject
    @MockMode("prod")
    MSTimelineService service;

    public TaskGetTimeLineConfig() {
    }

    @Override
    public Observable<HashMap<String, List<TimelineCategoryResponse.CategoryItem>>> execute(TaskResource res) {
        return service.getTimelineConfig().switchMap(timelineUrlsResponse -> {
            if (timelineUrlsResponse != null && timelineUrlsResponse.data != null && timelineUrlsResponse.data.size() > 0) {
                final List<String> categories = new ArrayList<>();
                for (int i = 0; i < timelineUrlsResponse.data.size(); i++) {
                    categories.add(timelineUrlsResponse.data.get(i).name);
                }

                List<Observable<TimelineCategoryResponse>> observableList = new ArrayList<>();
                for (int i = 0; i < timelineUrlsResponse.data.size(); i++) {
                    observableList.add(service.getCategoryData(timelineUrlsResponse.data.get(i).url));
                }

                return Observable.combineLatest(observableList, objects -> {
                    HashMap<String, List<TimelineCategoryResponse.CategoryItem>> result = new HashMap<>();
                    for (int i = 0; i < objects.length; i++) {
                        if (objects[i] instanceof TimelineCategoryResponse) {
                            result.put(categories.get(i), ((TimelineCategoryResponse) objects[i]).data);
                        }
                    }

                    return result;
                });
            }

            return null;
        });
    }
}
