package com.blackducksoftware.integration.hub.api.scan;

import java.util.Date;

import com.blackducksoftware.integration.hub.api.item.HubItem;
import com.blackducksoftware.integration.hub.meta.MetaInformation;
import com.blackducksoftware.integration.hub.scan.status.ScanStatus;

public class ScanSummaryItem extends HubItem {
	private final ScanStatus status;
	private final String statusMessage;
	private final Date createdAt;
	private final Date updatedAt;

	public ScanSummaryItem(final ScanStatus status, final String statusMessage, final Date createdAt,
			final Date updatedAt, final MetaInformation meta) {
		super(meta);
		this.status = status;
		this.statusMessage = statusMessage;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public ScanStatus getStatus() {
		return status;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((statusMessage == null) ? 0 : statusMessage.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ScanSummaryItem other = (ScanSummaryItem) obj;
		if (createdAt == null) {
			if (other.createdAt != null) {
				return false;
			}
		} else if (!createdAt.equals(other.createdAt)) {
			return false;
		}
		if (status != other.status) {
			return false;
		}
		if (statusMessage == null) {
			if (other.statusMessage != null) {
				return false;
			}
		} else if (!statusMessage.equals(other.statusMessage)) {
			return false;
		}
		if (updatedAt == null) {
			if (other.updatedAt != null) {
				return false;
			}
		} else if (!updatedAt.equals(other.updatedAt)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ScanSummaryItem [status=" + status + ", statusMessage=" + statusMessage + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + "]";
	}

}
