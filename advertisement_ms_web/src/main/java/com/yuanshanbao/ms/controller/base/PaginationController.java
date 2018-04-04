package com.yuanshanbao.ms.controller.base;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yuanshanbao.paginator.domain.Order;
import com.yuanshanbao.paginator.domain.Order.Direction;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

public class PaginationController extends BaseController {

	protected static final String RANGE_PREFIX = "items=";

	protected static final String CONTENT_RANGE_HEADER = "Content-Range";

	protected static final String ACCEPT_JSON = "Accept=application/json";

	// protected BaseService<T> baseService;
	//
	// @RequestMapping(value="/{id}", method=RequestMethod.GET,
	// headers=ACCEPT_JSON)
	// public @ResponseBody T getJson(@PathVariable("id") String id) {
	// return baseService.getById(id);
	// }
	//
	// /**
	// * TODO - Should probably be stricter here in following the HTTP spec,
	// * i.e. returning the proper 20x response instead of an explicit redirect
	// */
	// @RequestMapping(method=RequestMethod.POST, headers=ACCEPT_JSON)
	// public String create(@Validated @RequestBody T t) {
	// String id = baseService.insert(t);
	// return "redirect:/usstates/" + id;
	// }
	//
	// @RequestMapping(value="/{id}", method={RequestMethod.POST,
	// RequestMethod.PUT}, headers={ACCEPT_JSON,"If-None-Match=*"})
	// public String createJsonWithId(@Validated @RequestBody T t,
	// @PathVariable("id") String id) {
	// return baseService.update(t, id);
	// }
	//
	// @RequestMapping(value="/{id}", method={RequestMethod.POST,
	// RequestMethod.PUT}, headers={ACCEPT_JSON,"If-Match=*"})
	// public String overWriteJson(@Validated @RequestBody T t,
	// @PathVariable("id") String id) {
	// return baseService.update(t, id);
	// }
	//
	// // /**
	// // * TODO - Should probably be stricter here in following the HTTP spec,
	// // * i.e. returning the proper 20x response instead of an automatic
	// redirect
	// // */
	// // @RequestMapping(value="/{id}", method=RequestMethod.PUT,
	// headers=ACCEPT_JSON)
	// // public String updateJson(@Validated @RequestBody USState state,
	// @PathVariable("id") Long id) {
	// // state.merge();
	// // return "redirect:/usstates/"+state.getId();
	// // }
	//
	// @RequestMapping(method=RequestMethod.DELETE, headers=ACCEPT_JSON)
	// public @ResponseBody ResponseEntity<String> delete(@PathVariable("id")
	// String id) {
	// baseService.deleteById(id);
	// return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
	// }
	//
	// @RequestMapping(method=RequestMethod.GET, headers=ACCEPT_JSON)
	// public @ResponseBody HttpEntity<List<T>> list() {
	// HttpHeaders headers = new HttpHeaders();
	// List<T> body = null;
	// body = baseService.listAll();
	// headers.add(CONTENT_RANGE_HEADER, getContentRangeValue(0, body.size(),
	// new Integer(body.size())));
	// return new HttpEntity<List<T>>(body, headers);
	// }
	//
	// public @ResponseBody HttpEntity<List<T>>
	// listJsonForRange(@RequestHeader(value="Range") String range,
	// HttpServletRequest request) {
	// @RequestMapping(method=RequestMethod.GET, headers={ACCEPT_JSON, "Range"})
	// HttpHeaders headers = new HttpHeaders();
	// List<T> body = null;
	// Range parsedRange = new Range(range.replaceAll(RANGE_PREFIX, ""));
	// int count = baseService.getCount();
	// body = baseService.getRangeResult(parsedRange.getFirstResult(),
	// parsedRange.getMaxResults());
	// headers.add(CONTENT_RANGE_HEADER,
	// getContentRangeValue(parsedRange.getFirstResult(), body.size(), count));
	// return new HttpEntity<List<T>>(body, headers);
	// }
	//
	// /**
	// * TODO - This doesn't actually get selected since the query param is in
	// the form of 'sort(...)' instead of 'sort=(...)'
	// */
	// @RequestMapping(method=RequestMethod.GET, headers={ACCEPT_JSON, "Range"},
	// params="sort")
	// public @ResponseBody HttpEntity<List<T>>
	// listForRangeSorted(@RequestHeader("Range") String range,
	// @RequestParam("sort") String sort) {
	// HttpHeaders headers = new HttpHeaders();
	// List<T> body = null;
	// Range parsedRange = new Range(range.replaceAll(RANGE_PREFIX, ""));
	// int count = baseService.getCount();
	// //TODO - Implement sort param parsing
	// body = baseService.getRangeSortedResult(parsedRange.getFirstResult(),
	// parsedRange.getMaxResults(), "", false);
	// headers.add(CONTENT_RANGE_HEADER,
	// getContentRangeValue(parsedRange.getFirstResult(), body.size(), count));
	// return new HttpEntity<List<T>>(body, headers);
	// }

	protected Order getOrderFromRequest(HttpServletRequest request) {
		Order order = null;
		@SuppressWarnings("rawtypes")
		Set parameters = request.getParameterMap().keySet();
		for (Object param : parameters) {
			try {
				String paramStr = param.toString();
				if (paramStr.startsWith("sort(")) {
					String property = paramStr.substring("sort(".length() + 1, paramStr.length() - 1);
					String directionStr = URLEncoder.encode(paramStr.substring("sort(".length(), "sort(".length() + 1),
							"UTF-8");
					if ("+".equals(directionStr)) {
						order = new Order(property, Direction.ASC, null);
					} else if ("-".equals(directionStr)) {
						order = new Order(property, Direction.DESC, null);
					}
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		return order;
	}

	protected PageBounds getPageBounds(String range, HttpServletRequest request) {
		Order order = getOrderFromRequest(request);
		int start = Integer.parseInt(request.getParameter("start"));
		int length = Integer.parseInt(request.getParameter("length"));
		int page = (start / length) + 1;
		PageBounds pageBounds = null;
		if (order == null) {
			pageBounds = new PageBounds(page, length);
		} else {
			pageBounds = new PageBounds(page, length, order);
		}

		return pageBounds;
	}
	
	protected PageBounds getPageBounds(HttpServletRequest request) {
		return getPageBounds(null, request);
	}

	protected Map<String, Object> setPageInfo(HttpServletRequest request, HttpServletResponse response, @SuppressWarnings("rawtypes") PageList pageList) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("draw", request.getParameter("draw"));
		result.put("recordsTotal", pageList.getTotalCount());
		result.put("recordsFiltered", pageList.getTotalCount());
		result.put("data", pageList);
		return result;
	}

	protected String getContentRangeValue(Integer firstResult, Integer resultCount, Integer totalCount) {
		StringBuilder value = new StringBuilder("items " + firstResult + "-");
		if (resultCount == 0) {
			value.append("0");
		} else {
			value.append(firstResult + resultCount - 1);
		}
		value.append("/" + totalCount);
		return value.toString();
	}

	protected static final class Range {
		private Integer page = 0;

		private Integer limit = 0;

		public Range(String range) {
			String[] parsed = range.split("-");
			Integer begin = new Integer(parsed[0]);
			Integer end = new Integer(parsed[1]);
			this.limit = end - begin + 1;
			this.page = begin / limit + 1;
		}

		public Integer getPage() {
			return page;
		}

		public void setPage(Integer page) {
			this.page = page;
		}

		public Integer getLimit() {
			return limit;
		}

		public void setLimit(Integer limit) {
			this.limit = limit;
		}
	}
}