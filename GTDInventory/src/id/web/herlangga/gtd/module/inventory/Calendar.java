package id.web.herlangga.gtd.module.inventory;

import com.domainlanguage.time.*;
import id.web.herlangga.gtd.module.inventory.item.*;

public interface Calendar {
    public ScheduledStuff[] scheduledStuffs();
    
    public ScheduledStuff[] scheduledStuffsFor(CalendarDate calendarDate);
}
