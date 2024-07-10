package org.velog.api.utils.hateoas.target.ifs;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.hateoas.EntityModel;

public interface HateoasIfs {
    EntityModel<?> getResourceLink(EntityModel<?> resource);
}
