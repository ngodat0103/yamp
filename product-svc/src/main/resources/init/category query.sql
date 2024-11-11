
-- CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
-- INSERT INTO category(uuid, name, thumbnail_url, slug_name,created_at,last_modified_at,created_by,last_modified_by)
-- VALUES
-- (uuid_generate_v4(), 'Laptop', 'https://images.fpt.shop/unsafe/fit-in/60x60/filters:quality(90):fill(transparent)/fptshop.com.vn/Uploads/images/2022/iconcate/icon-laptop.png', 'laptop',now(),now(),'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9','f9ef8aa2-260b-4c32-ae25-7c406e83f0b9'),
-- (uuid_generate_v4(), 'Điện thoại', 'https://images.fpt.shop/unsafe/fit-in/60x60/filters:quality(90):fill(transparent)/fptshop.com.vn/Uploads/images/2015/ic-dienthoai-desktop.png', 'dien-thoai',now(),now(),'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9','f9ef8aa2-260b-4c32-ae25-7c406e83f0b9'),
-- (uuid_generate_v4(), 'Máy tính bảng', 'https://images.fpt.shop/unsafe/fit-in/60x60/filters:quality(90):fill(transparent)/fptshop.com.vn/Uploads/images/2015/icon-mtb-desk.png', 'may-tinh-bang',now(),now(),'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9','f9ef8aa2-260b-4c32-ae25-7c406e83f0b9'),
-- (uuid_generate_v4(), 'Đồng hồ thông minh', 'https://images.fpt.shop/unsafe/fit-in/60x60/filters:quality(90):fill(transparent)/fptshop.com.vn/Uploads/images/2022/iconcate/icon-smartwatch.png', 'dong-ho-thong-minh',now(),now(),'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9','f9ef8aa2-260b-4c32-ae25-7c406e83f0b9'),
-- (uuid_generate_v4(), 'Tai nghe', 'https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn//content/Tainghe-128x129.png', 'tai-nghe',now(),now(),'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9','f9ef8aa2-260b-4c32-ae25-7c406e83f0b9'),
-- (uuid_generate_v4(), 'Chuột máy tính', 'https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/c/h/chuot-khong-day-logitech-mx-master-3-1_2_1.png', 'chuot-may-tinh',now(),now(),'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9','f9ef8aa2-260b-4c32-ae25-7c406e83f0b9'),
-- (uuid_generate_v4(), 'Màn hình', 'https://images.fpt.shop/unsafe/fit-in/60x60/filters:quality(90):fill(transparent)/fptshop.com.vn/Uploads/images/2022/iconcate/icon-screen.png', 'man-hinh',now(),now(),'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9','f9ef8aa2-260b-4c32-ae25-7c406e83f0b9'),
-- (uuid_generate_v4(), 'Cáp sạc', 'https://images.fpt.shop/unsafe/fit-in/750x500/filters:quality(90):fill(white)/fptshop.com.vn/Uploads/Originals/2023/5/30/638210533611674536_LIGHTNING-MFI-BELKIN-PVC-MAUDEN-2.jpg', 'cap-sac',now(),now(),'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9','f9ef8aa2-260b-4c32-ae25-7c406e83f0b9'),
-- (uuid_generate_v4(), 'Củ sạc', 'https://images.fpt.shop/unsafe/fit-in/214x214/filters:quality(90):fill(white)/fptshop.com.vn/Uploads/Originals/2020/10/20/637387863045167961_pk-apple-00720432-dd.png', 'cu-sac',now(),now(),'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9','f9ef8aa2-260b-4c32-ae25-7c406e83f0b9'),
-- (uuid_generate_v4(), 'Loa', 'https://images.fpt.shop/unsafe/fit-in/214x214/filters:quality(90):fill(white)/fptshop.com.vn/Uploads/Originals/2019/12/26/637129512862634615_00515659.jpg', 'loa',now(),now(),'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9','f9ef8aa2-260b-4c32-ae25-7c406e83f0b9'),
-- (uuid_generate_v4(), 'Bàn phím', 'https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn//content/ban-phim-128x129.png', 'ban-phim',now(),now(),'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9','f9ef8aa2-260b-4c32-ae25-7c406e83f0b9'),
-- (uuid_generate_v4(), 'Camera', 'https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn//content/icon-camera-128x129.png', 'camera',now(),now(),'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9','f9ef8aa2-260b-4c32-ae25-7c406e83f0b9');


INSERT INTO public.category (created_at, last_modified_at, created_by, last_modified_by, parent_category_uuid, uuid, name, slug_name, thumbnail_url) VALUES ('2024-09-05 07:31:34.942271', '2024-09-05 07:31:34.942271', 'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9', 'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9', null, '9340394f-7e1e-447f-88df-62a67365c649', 'Laptop', 'laptop', 'https://images.fpt.shop/unsafe/fit-in/60x60/filters:quality(90):fill(transparent)/fptshop.com.vn/Uploads/images/2022/iconcate/icon-laptop.png');
INSERT INTO public.category (created_at, last_modified_at, created_by, last_modified_by, parent_category_uuid, uuid, name, slug_name, thumbnail_url) VALUES ('2024-09-05 07:31:34.942271', '2024-09-05 07:31:34.942271', 'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9', 'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9', null, '6d81097f-1fbf-4125-9ccc-66337b0d46f2', 'Điện thoại', 'dien-thoai', 'https://images.fpt.shop/unsafe/fit-in/60x60/filters:quality(90):fill(transparent)/fptshop.com.vn/Uploads/images/2015/ic-dienthoai-desktop.png');
INSERT INTO public.category (created_at, last_modified_at, created_by, last_modified_by, parent_category_uuid, uuid, name, slug_name, thumbnail_url) VALUES ('2024-09-05 07:31:34.942271', '2024-09-05 07:31:34.942271', 'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9', 'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9', null, 'dc00de43-7945-466b-a753-473ed55f4b72', 'Máy tính bảng', 'may-tinh-bang', 'https://images.fpt.shop/unsafe/fit-in/60x60/filters:quality(90):fill(transparent)/fptshop.com.vn/Uploads/images/2015/icon-mtb-desk.png');
INSERT INTO public.category (created_at, last_modified_at, created_by, last_modified_by, parent_category_uuid, uuid, name, slug_name, thumbnail_url) VALUES ('2024-09-05 07:31:34.942271', '2024-09-05 07:31:34.942271', 'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9', 'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9', null, 'a69ffd3a-1080-43e5-9cf4-d5e05672f3a3', 'Đồng hồ thông minh', 'dong-ho-thong-minh', 'https://images.fpt.shop/unsafe/fit-in/60x60/filters:quality(90):fill(transparent)/fptshop.com.vn/Uploads/images/2022/iconcate/icon-smartwatch.png');
INSERT INTO public.category (created_at, last_modified_at, created_by, last_modified_by, parent_category_uuid, uuid, name, slug_name, thumbnail_url) VALUES ('2024-09-05 07:31:34.942271', '2024-09-05 07:31:34.942271', 'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9', 'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9', null, '3641ec73-9024-464a-96d9-0d827498a699', 'Tai nghe', 'tai-nghe', 'https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn//content/Tainghe-128x129.png');
INSERT INTO public.category (created_at, last_modified_at, created_by, last_modified_by, parent_category_uuid, uuid, name, slug_name, thumbnail_url) VALUES ('2024-09-05 07:31:34.942271', '2024-09-05 07:31:34.942271', 'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9', 'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9', null, '7e39d9c0-fc71-449a-a12d-57b4e237f0c8', 'Chuột máy tính', 'chuot-may-tinh', 'https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/c/h/chuot-khong-day-logitech-mx-master-3-1_2_1.png');
INSERT INTO public.category (created_at, last_modified_at, created_by, last_modified_by, parent_category_uuid, uuid, name, slug_name, thumbnail_url) VALUES ('2024-09-05 07:31:34.942271', '2024-09-05 07:31:34.942271', 'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9', 'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9', null, 'd0580eaa-c133-422d-9249-47d05511958b', 'Màn hình', 'man-hinh', 'https://images.fpt.shop/unsafe/fit-in/60x60/filters:quality(90):fill(transparent)/fptshop.com.vn/Uploads/images/2022/iconcate/icon-screen.png');
INSERT INTO public.category (created_at, last_modified_at, created_by, last_modified_by, parent_category_uuid, uuid, name, slug_name, thumbnail_url) VALUES ('2024-09-05 07:31:34.942271', '2024-09-05 07:31:34.942271', 'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9', 'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9', null, '5403de3b-79b4-4f49-a7eb-13832c1b616d', 'Cáp sạc', 'cap-sac', 'https://images.fpt.shop/unsafe/fit-in/750x500/filters:quality(90):fill(white)/fptshop.com.vn/Uploads/Originals/2023/5/30/638210533611674536_LIGHTNING-MFI-BELKIN-PVC-MAUDEN-2.jpg');
INSERT INTO public.category (created_at, last_modified_at, created_by, last_modified_by, parent_category_uuid, uuid, name, slug_name, thumbnail_url) VALUES ('2024-09-05 07:31:34.942271', '2024-09-05 07:31:34.942271', 'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9', 'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9', null, '030bf174-f7e4-445a-bf72-5397a3581bd1', 'Củ sạc', 'cu-sac', 'https://images.fpt.shop/unsafe/fit-in/214x214/filters:quality(90):fill(white)/fptshop.com.vn/Uploads/Originals/2020/10/20/637387863045167961_pk-apple-00720432-dd.png');
INSERT INTO public.category (created_at, last_modified_at, created_by, last_modified_by, parent_category_uuid, uuid, name, slug_name, thumbnail_url) VALUES ('2024-09-05 07:31:34.942271', '2024-09-05 07:31:34.942271', 'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9', 'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9', null, '88b621ac-69e8-4d56-a002-97f088e66df9', 'Loa', 'loa', 'https://images.fpt.shop/unsafe/fit-in/214x214/filters:quality(90):fill(white)/fptshop.com.vn/Uploads/Originals/2019/12/26/637129512862634615_00515659.jpg');
INSERT INTO public.category (created_at, last_modified_at, created_by, last_modified_by, parent_category_uuid, uuid, name, slug_name, thumbnail_url) VALUES ('2024-09-05 07:31:34.942271', '2024-09-05 07:31:34.942271', 'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9', 'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9', null, '96bc58ec-65be-49df-88dc-2ea4ce796938', 'Bàn phím', 'ban-phim', 'https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn//content/ban-phim-128x129.png');
INSERT INTO public.category (created_at, last_modified_at, created_by, last_modified_by, parent_category_uuid, uuid, name, slug_name, thumbnail_url) VALUES ('2024-09-05 07:31:34.942271', '2024-09-05 07:31:34.942271', 'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9', 'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9', null, 'b6e78609-f55b-42ba-8c3e-3dcb298cdc53', 'Camera', 'camera', 'https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn//content/icon-camera-128x129.png');
