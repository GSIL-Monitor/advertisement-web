package com.yuanshanbao.common.util;

public class ChangeToCamel {

	public static void main(String[] args) {
		String content = "merchant_id}, #{name}, #{merchant_product_id}, #{keywords}, #{full_name}, #{short_name}, #{english_name}, #{introduction}, #{description}, #{image_url}, #{detail_image_url}, #{show_price}, #{price}, #{cost_price}, #{rate}, #{store}, #{store_type}, #{sale_count}, #{view_count}, #{priority}, #{show_tags}, #{tips}, #{type}, #{bill_type}, #{can_deliver}, #{can_refund}, #{can_settle_up}, #{pricing_way}, #{pricing_fomula}, #{underwrite_url}, #{second_underwrite_url}, #{query_url}, #{deliver_order_url}, #{cancel_order_url}, #{withdraw_url}, #{sale_merchant_id}, #{status}, #{merchant_status}, #{create_time}, #{update_time}, #{publish_time}, #{close_time";
		changeToCamel(content);
	}

	private static void changeToCamel(String content) {
		String[] segs = content.split("_");
		for (int i = 0; i < segs.length; i++) {
			if (i > 0) {
				System.out.print(segs[i].substring(0, 1).toUpperCase() + segs[i].substring(1));
			} else {
				System.out.print(segs[i]);
			}
		}
	}

}
