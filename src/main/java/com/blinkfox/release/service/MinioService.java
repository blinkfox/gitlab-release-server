package com.blinkfox.release.service;

import com.blinkfox.release.config.SystemConfig;
import com.blinkfox.release.consts.Const;
import com.blinkfox.release.exception.RunException;
import com.blinkfox.release.kits.StringKit;

import io.minio.MinioClient;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * Minio 相关操作的 Service 服务类.
 *
 * @author chenjiayin on 2019-06-16.
 */
@Slf4j
@Service
public class MinioService {

    @Resource
    private SystemConfig systemConfig;

    private MinioClient minioClient;

    /**
     * MinIO 的访问地址.
     */
    private String endpoint;

    /**
     * MinIO 的桶.
     */
    private String bucket;

    /**
     * Bean 创建后初始化 MinioClient 实例，并检查是否存在配置的桶，如果不存在就创建一个.
     * <p>注：你需要去 MinIO 中将这个桶设置为只读.</p>
     */
    @PostConstruct
    public void init() {
        try {
            this.endpoint = systemConfig.getEndpoint();
            endpoint = endpoint.endsWith(Const.SEP) ? endpoint.substring(0, endpoint.length() - 1) : endpoint;
            this.minioClient = new MinioClient(endpoint, systemConfig.getAccessKey(), systemConfig.getSecretKey());
            this.bucket = systemConfig.getBucket();
            this.checkAndInitBucket();
        } catch (Exception e) {
            log.error("初始化创建 MinioClient 出错，请检查！", e);
        }
    }

    /**
     * 检查 bucket 是否存在，如果不存在则创建一个.
     */
    private void checkAndInitBucket() {
        try {
            // 如果该桶不存在，就创建一个，目前需要人为手动配置该桶为只读，后续可通过程序来设置.
            if (!this.minioClient.bucketExists(this.bucket)) {
                log.warn("【{}】的桶不存在，将会创建一个。", this.bucket);
                this.minioClient.makeBucket(this.bucket);
                log.info("【{}】的桶已经创建成功，目前请手动设置该桶为只读。", this.bucket);
            }
        } catch (Exception e) {
            throw new RunException(StringKit.format("检查或初始化Minio中名为【{}】的桶是否存在出错!", bucket), e);
        }
    }

    /**
     * 上传文件到 Minio 中，并返回该对象可访问的 URL 地址.
     *
     * @param objectName 对象名.
     * @param in 输入流
     * @return URL 地址
     */
    public String putObject(String objectName, InputStream in) {
        try {
            minioClient.putObject(bucket,  objectName, in, ContentType.APPLICATION_OCTET_STREAM.toString());
            log.info("上传文件到 Minio 中成功.");
            return StringUtils.join(endpoint, Const.SEP, bucket, Const.SEP, objectName);
        } catch (Exception e) {
            throw new RunException("上传文件过程中发生了错误!", e);
        }
    }

}
